package com.example.user.service;

import com.example.user.domain.entity.Role;
import com.example.user.domain.entity.Session;
import com.example.user.domain.entity.User;
import com.example.user.domain.repository.SessionRepository;
import com.example.user.domain.repository.UserRepository;
import com.example.user.dto.jwt.JwtResponseDto;
import com.example.user.dto.userResponces.LoginUserDto;
import com.example.user.dto.userResponces.RegisterUserDto;
import com.example.user.exceptions.InvalidAuthOperationException;
import com.example.user.service.constants.ErrorMessagesConstants;
import com.example.user.service.constants.UserAuthConstants;
import com.example.user.utils.Keys;
import com.example.user.utils.MD5HashFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * AuthService
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    /**
     * Register user
     * @param token jwt token
     * @return JwtResponseDto
     * @throws InvalidAuthOperationException if token is invalid
     */
    public JwtResponseDto getAccessToken(String token) throws InvalidAuthOperationException {
        if (!sessionRepository.existsBySessionToken(token)) {
            throw new InvalidAuthOperationException(HttpStatus.NOT_FOUND, "Invalid token");
        }

        Session session = sessionRepository.findBySessionToken(token);
        if (session.getExpiresAt().before(Timestamp.valueOf(LocalDateTime.now()))) {
            sessionRepository.deleteById(session.getId());
            throw new InvalidAuthOperationException(HttpStatus.UNAUTHORIZED, "Token expired");
        }

        User user = session.getUser();
        return createNewToken(user);
    }

    /**
     * Register user
     * @param user user
     * @return JwtResponseDto
     * @throws InvalidAuthOperationException if token is invalid
     */
    private JwtResponseDto createNewToken(User user) throws InvalidAuthOperationException {
        String jsonEncoded = JwtSignatureCreator.getEncodedJson(user);
        String signature = JwtSignatureCreator.getSignature(jsonEncoded, Keys.secret_key);
        if ("".equals(signature)) {
            throw new InvalidAuthOperationException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessagesConstants.INTERNAL_SERVER_ERROR);
        }
        String token = String.format("%s.%s", jsonEncoded, signature);
        String refreshToken = KeyGenerator.generateToken();

        if (sessionRepository.existsByUserId(user.getId())) {
            Session session = sessionRepository.findByUserId(user.getId())
                    .setSessionToken(token)
                    .setExpiresAt(Timestamp.valueOf(LocalDateTime.now().
                            plusMinutes(JwtSignatureCreator.REFRESH_TOKEN_EXPIRATION_TIME)));
            sessionRepository.save(session);
        } else {
            Session session = new Session()
                    .setSessionToken(token)
                    .setExpiresAt(Timestamp.valueOf(LocalDateTime.now().
                            plusMinutes(JwtSignatureCreator.REFRESH_TOKEN_EXPIRATION_TIME)))
                    .setUser(user);
            user.setSession(session);
            session.setUser(user);
            userRepository.save(user);
        }
        return new JwtResponseDto().setAccessToken(token).setRefreshToken(refreshToken);
    }

    /**
     * Login user
     * @param registerUserDto user
     * @return JwtResponseDto
     * @throws InvalidAuthOperationException if token is invalid
     */
    public String register(RegisterUserDto registerUserDto) throws InvalidAuthOperationException {
        String userName = registerUserDto.getUsername();
        String email = registerUserDto.getEmail();
        checkUsernameAndEmail(userName, email);
        String passwordHash = MD5HashFunction.getMD5Hash(registerUserDto.getPassword());
        userRepository.save(new User()
                .setRole(Role.CUSTOMER)
                .setEmail(email)
                .setUsername(userName)
                .setPasswordHash(passwordHash)
                .setCreatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()))
        );
        return "Successfully registered";
    }

    /**
     * Login user
     * @param userName
     * @param email
     * @throws InvalidAuthOperationException
     */
    private void checkUsernameAndEmail(String userName, String email) throws InvalidAuthOperationException {
        if (!userName.matches("^[A-Za-z0-9_]{3,15}$")) {
            throw new InvalidAuthOperationException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    UserAuthConstants.INVALID_USERNAME
            );
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidAuthOperationException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    UserAuthConstants.INVALID_EMAIL
            );
        }

        if (userRepository.existsByUsername(userName)) {
            throw new InvalidAuthOperationException(
                    HttpStatus.CONFLICT,
                    UserAuthConstants.USERNAME_IS_TAKEN
            );
        }
        if (userRepository.existsByEmail(email)) {
            throw new InvalidAuthOperationException(
                    HttpStatus.CONFLICT,
                    UserAuthConstants.EMAIL_IS_TAKEN
            );
        }
    }

    /**
     * Login user
     * @param authRequest user
     * @return JwtResponseDto
     * @throws InvalidAuthOperationException if token is invalid
     */
    public JwtResponseDto login(LoginUserDto authRequest) throws InvalidAuthOperationException {
        boolean emailExists = userRepository.existsByEmail(authRequest.getEmail());
        if (!emailExists) {
            throw new InvalidAuthOperationException(HttpStatus.NOT_FOUND,
                    UserAuthConstants.EMAIL_NOT_FOUND);
        }
        User user = userRepository.findByEmail(authRequest.getEmail());
        String storedHashedPassword = user.getPasswordHash();
        if (!storedHashedPassword.equals(MD5HashFunction.getMD5Hash(authRequest.getPassword()))) {
            throw new InvalidAuthOperationException(HttpStatus.CONFLICT,
                    UserAuthConstants.WRONG_PASSWORD);
        }
        return createNewToken(user);
    }


}
