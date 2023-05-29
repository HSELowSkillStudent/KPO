package org.example.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * DishDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class DishDto {
    private String name;
    private Integer quantity;
}
