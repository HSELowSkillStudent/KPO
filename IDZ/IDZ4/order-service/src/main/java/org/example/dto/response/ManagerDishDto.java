package org.example.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ManagerDishDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class ManagerDishDto extends DishResponseDto {
    private Integer id;
    private Integer quantity;
}
