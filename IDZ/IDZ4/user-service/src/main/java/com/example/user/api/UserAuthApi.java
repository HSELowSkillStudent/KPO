package com.example.user.api;

import com.example.user.dto.jwt.JwtRefreshDto;
import com.example.user.dto.userResponces.LoginUserDto;
import com.example.user.dto.userResponces.RegisterUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UserAuthApi
 */
@RequestMapping("/auth")
public interface UserAuthApi {
    /**
     * Register
     * @param registrationRequestDto - registrationRequestDto
     * @return ResponseEntity<?>
     */
    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterUserDto registrationRequestDto);

    /**
     * Login
     * @param authRequest authRequest
     * @return ResponseEntity<?>
     */
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginUserDto authRequest);

    /**
     * Get new access and refresh token
     * @param refreshRequest refreshRequest
     * @return ResponseEntity<?>
     */
    @PostMapping("/token")
    ResponseEntity<?> getNewAccessAndRefreshToken(@RequestBody JwtRefreshDto refreshRequest);
}
