package org.example.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ClientDishDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class ClientDishDto extends DishResponseDto {
    private Boolean available;
}
