package com.example.user.dto.userResponces;

import com.example.user.domain.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * UserDto
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserDto {
    private Integer id;
    private String username;
    private Role role;
}
