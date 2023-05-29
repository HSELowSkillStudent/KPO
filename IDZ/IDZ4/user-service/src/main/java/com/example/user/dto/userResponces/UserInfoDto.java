package com.example.user.dto.userResponces;

import com.example.user.domain.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * UserInfoDto
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserInfoDto {
    private String username;
    private Role role;
    private String message;
}
