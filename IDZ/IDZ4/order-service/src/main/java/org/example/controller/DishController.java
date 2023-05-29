package org.example.controller;

import org.example.api.DishApi;
import org.example.dto.request.CreateDishDto;
import org.example.dto.request.DeleteDishDto;
import org.example.service.DishService;
import org.example.exception.InvalidOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DishController implements DishApi {

    private final DishService dishService;

    /**
     * Get menu
     * @param token JWT token
     * @return menu or error message
     */
    @Override
    public ResponseEntity<?> getMenu(String token) {
        try {
            return ResponseEntity.ok(dishService.getMenu(token));
        } catch (InvalidOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    /**
     * Add dish
     * @param token JWT token
     * @param dishRequestDto dish dto
     * @return message or error message
     */
    @Override
    public ResponseEntity<?> addDish(String token, CreateDishDto dishRequestDto) {
        try {
            return ResponseEntity.ok(dishService.addDish(token, dishRequestDto));
        } catch (InvalidOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    /**
     * Delete dish
     * @param token JWT token
     * @param requestDto dish dto
     * @return message or error message
     */
    @Override
    public ResponseEntity<?> deleteDish(String token, DeleteDishDto requestDto) {
        try {
            return ResponseEntity.ok(dishService.deleteDish(token, requestDto));
        } catch (InvalidOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}
