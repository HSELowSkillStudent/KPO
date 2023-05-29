package com.example.user.api;

import com.example.user.dto.userResponces.ChangeRoleUserDto;
import com.example.user.dto.userResponces.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserControllerApi
 */
@RequestMapping("/user")
public interface UserControllerApi {
    /**
     * Get user info
     * @param accessToken accessToken
     * @return ResponseEntity<?>
     */
    @GetMapping("/get_info")
    ResponseEntity<?> checkAccess(@RequestHeader("Authorization") String accessToken);

    /**
     * Get user list
     * @param accessToken accessToken
     * @return ResponseEntity<?>
     */
    @GetMapping("/get_user_list")
    ResponseEntity<?> getUserList(@RequestHeader("Authorization") String accessToken);

    /**
     * Get user by id
     * @param accessToken accessToken
     * @param userDto userDto
     * @return ResponseEntity<?>
     */
    @PostMapping("/change_role")
    ResponseEntity<?> changeRoleById(@RequestHeader("Authorization") String accessToken,
                                     @RequestBody ChangeRoleUserDto userDto);
}
