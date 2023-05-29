package com.example.user.controller;

import com.example.user.api.UserAuthApi;
import com.example.user.dto.jwt.JwtRefreshDto;
import com.example.user.dto.userResponces.LoginUserDto;
import com.example.user.dto.userResponces.RegisterUserDto;
import com.example.user.exceptions.InvalidAuthOperationException;
import com.example.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TokenController
 */
@RestController
@RequiredArgsConstructor
public class TokenController implements UserAuthApi {
    public final AuthService authService;

    /**
     * Register
     * @param registrationRequestDto registrationRequestDto
     * @return ResponseEntity<?>
     */
    @Override
    public ResponseEntity<?> register(RegisterUserDto registrationRequestDto) {
        try {
            return ResponseEntity.ok(authService.register(registrationRequestDto));
        } catch (InvalidAuthOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    /**
     * Login
     * @param authRequest authRequest
     * @return ResponseEntity<?>
     */
    @Override
    public ResponseEntity<?> login(LoginUserDto authRequest) {
        try {
            return ResponseEntity.ok(authService.login(authRequest));
        } catch (InvalidAuthOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    /**
     * Get new access and refresh token
     * @param refreshRequest refreshRequest
     * @return ResponseEntity<?>
     */
    @Override
    public ResponseEntity<?> getNewAccessAndRefreshToken(JwtRefreshDto refreshRequest) {
        try {
            return ResponseEntity.ok(authService.getAccessToken(refreshRequest.getRefreshToken()));
        } catch (InvalidAuthOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}
