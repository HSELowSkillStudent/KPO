package org.example.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * CreateDishDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class CreateDishDto {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
