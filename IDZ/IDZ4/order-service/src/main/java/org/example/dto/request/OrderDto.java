package org.example.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * OrderDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class OrderDto {
    private List<DishDto> dishDtoList;
    private String specialRequests;
}
