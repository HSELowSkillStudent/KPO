package com.example.user.service;

import com.example.user.domain.entity.Role;
import com.example.user.domain.entity.User;
import com.example.user.domain.repository.UserRepository;
import com.example.user.dto.jwt.JwtPayloadDto;
import com.example.user.dto.userResponces.UserDto;
import com.example.user.dto.userResponces.UserInfoDto;
import com.example.user.exceptions.UserServiceException;
import com.example.user.service.constants.ErrorMessagesConstants;
import com.example.user.service.constants.UserAuthConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Get user info by token
     *
     * @param token - jwt token
     * @return UserDto - user info
     * @throws UserServiceException - if token is invalid
     */
    public UserInfoDto getUserInfo(String token) throws UserServiceException {
        JwtPayloadDto jwtPayloadDto = getJwtDtoFromToken(token);
        UserInfoDto userInfoDto = new UserInfoDto()
                .setUsername(jwtPayloadDto.getUsername())
                .setRole(jwtPayloadDto.getRole());
        switch (jwtPayloadDto.getRole()) {
            case MANAGER -> userInfoDto.setMessage(UserAuthConstants.MANAGER_MESSAGE);
            case CHEF -> userInfoDto.setMessage(UserAuthConstants.CHEF_MESSAGE);
            case CUSTOMER -> userInfoDto.setMessage(UserAuthConstants.CUSTOMER_MESSAGE);
        }
        return userInfoDto;
    }

    /**
     * Get payload from token
     *
     * @param token - access token
     * @return JwtPayloadDto - payload from token
     * @throws UserServiceException - if token is invalid
     */
    private JwtPayloadDto getJwtDtoFromToken(String token) throws UserServiceException {
        if (!TokenChecker.isTokenValid(token)) {
            throw new UserServiceException(HttpStatus.UNAUTHORIZED, ErrorMessagesConstants.INVALID_ACCESS);
        }
        JwtPayloadDto payloadDto = TokenChecker.getPayload(token);
        if (payloadDto == null) {
            throw new UserServiceException(HttpStatus.UNAUTHORIZED, ErrorMessagesConstants.INVALID_ACCESS);
        }
        String payloadCheck = TokenChecker.checkPayload(payloadDto);
        if (!"ok".equals(payloadCheck)) {
            throw new UserServiceException(HttpStatus.UNAUTHORIZED, payloadCheck);
        }
        return payloadDto;
    }

    /**
     * Get list of users
     *
     * @param token - jwt token
     * @return list of users
     * @throws UserServiceException if token is invalid or user is not a manager
     */
    public List<UserDto> getUserList(String token) throws UserServiceException {
        JwtPayloadDto jwtPayloadDto = getJwtDtoFromToken(token);
        if (!Role.MANAGER.equals(jwtPayloadDto.getRole())) {
            throw new UserServiceException(HttpStatus.FORBIDDEN, ErrorMessagesConstants.FORBIDDEN);
        }
        List<User> users = userRepository.findAll();
        return users.stream().map(x -> new UserDto()
                .setId(x.getId())
                .setRole(x.getRole())
                .setUsername(x.getUsername())
        ).collect(Collectors.toList());
    }

    /**
     * Change user role
     * @param token - jwt token
     * @param userId - user id to change role
     * @param role - new role
     * @return "ok" if success or exception
     * @throws UserServiceException if token is invalid or user that change role is not a manager
     */
    public String changeUserRole(String token, Integer userId, Role role) throws UserServiceException {
        JwtPayloadDto jwtPayloadDto = getJwtDtoFromToken(token);
        if (!Role.MANAGER.equals(jwtPayloadDto.getRole())) {
            throw new UserServiceException(HttpStatus.FORBIDDEN, ErrorMessagesConstants.FORBIDDEN);
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserServiceException(HttpStatus.NOT_FOUND, ErrorMessagesConstants.USER_NOT_FOUND));
        user.setRole(role)
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
        return "ok";
    }
}
