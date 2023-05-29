package org.example.api;

import org.example.dto.request.CreateDishDto;
import org.example.dto.request.DeleteDishDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/dish")
public interface DishApi {
    /**
     * Get menu
     * @param token JWT token
     * @return menu
     */
    @GetMapping("/menu")
    ResponseEntity<?> getMenu(@RequestHeader("Authorization") String token);

    /**
     * Add dish
     * @param token JWT token
     * @param dishRequestDto dish dto
     * @return message
     */
    @PostMapping("/dishes")
    ResponseEntity<?> addDish(@RequestHeader("Authorization") String token,
                              @RequestBody CreateDishDto dishRequestDto);

    /**
     * Delete dish
     * @param token JWT token
     * @param requestDto dish dto
     * @return message
     */
    @PostMapping("/deleteDish")
    ResponseEntity<?> deleteDish(@RequestHeader("Authorization") String token,
                                 @RequestBody DeleteDishDto requestDto);
}
