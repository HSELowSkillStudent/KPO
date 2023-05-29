package com.example.user.dto.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * JwtRefreshDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class JwtRefreshDto {
    private String refreshToken;
}
