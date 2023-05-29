package org.example.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * OrderInfoDto
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class OrderInfoDto {
    private Integer orderId;
}
