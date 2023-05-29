package com.example.user.dto.userResponces;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * LoginUserDto
 */
@Getter
@Setter
@Accessors(chain = true)
public class LoginUserDto {
    private String email;
    private String password;
}
