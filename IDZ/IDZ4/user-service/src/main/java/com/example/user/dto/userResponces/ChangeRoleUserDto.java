package com.example.user.dto.userResponces;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ChangeRoleUserDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class ChangeRoleUserDto {
    private Integer userId;
    private String role;
}
