package com.example.user.controller;

import com.example.user.api.UserControllerApi;
import com.example.user.domain.entity.Role;
import com.example.user.dto.userResponces.ChangeRoleUserDto;
import com.example.user.exceptions.UserServiceException;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerApi {
    public final UserService userService;

    /**
     * Get user info
     * @param accessToken accessToken
     * @return ResponseEntity<?>
     */
    @Override
    public ResponseEntity<?> checkAccess(String accessToken) {
        try {
            return ResponseEntity.ok(userService.getUserInfo(accessToken));
        } catch (UserServiceException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    /**
     * Get user list
     * @param accessToken accessToken
     * @return ResponseEntity<?>
     */
    @Override
    public ResponseEntity<?> getUserList(String accessToken) {
        try {
            return ResponseEntity.ok(userService.getUserList(accessToken));
        } catch (UserServiceException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    /**
     * Change user role by id
     * @param accessToken accessToken
     * @param userDto userDto
     * @return ResponseEntity<?>
     */
    @Override
    public ResponseEntity<?> changeRoleById(String accessToken, ChangeRoleUserDto userDto) {
        try {
            return ResponseEntity.ok(userService.changeUserRole(
                    accessToken,
                    userDto.getUserId(),
                    Role.valueOf(userDto.getRole())));
        } catch (UserServiceException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}
