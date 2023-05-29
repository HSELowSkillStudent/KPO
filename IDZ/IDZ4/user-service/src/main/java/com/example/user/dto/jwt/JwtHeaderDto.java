package com.example.user.dto.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * JwtHeaderDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class JwtHeaderDto {
    private String typ;
    private String alg;
}
