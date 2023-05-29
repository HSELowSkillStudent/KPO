package org.example.dto.jwt;

import org.example.domain.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * JwtPayloadDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class JwtPayloadDto {
    private String iss;
    private Long exp;
    private Long nbf;
    private Role role;
    private String username;
}
