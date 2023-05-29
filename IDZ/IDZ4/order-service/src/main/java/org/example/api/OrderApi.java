package org.example.api;

import org.example.dto.request.OrderInfoDto;
import org.example.dto.request.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/order")
public interface OrderApi {
    /**
     * Create order
     * @param token JWT token
     * @param orderDto order dto
     * @return message
     */
    @PostMapping("/create")
    ResponseEntity<?> createOrder(@RequestHeader("Authorization") String token,
                                  @RequestBody OrderDto orderDto);

    /**
     * Get order list
     * @param token JWT token
     * @return order list
     */
    @PostMapping("/get_list")
    ResponseEntity<?> getOrderList(@RequestHeader("Authorization") String token);

    /**
     * Change order status
     * @param token JWT token
     * @param requestDto order dto
     * @return message
     */
    @PostMapping("/change_status")
    ResponseEntity<?> changeStatusById(@RequestHeader("Authorization") String token,
                                       @RequestBody OrderInfoDto requestDto);

    /**
     * Get order info
     * @param token JWT token
     * @param requestDto order dto
     * @return order info
     */
    @PostMapping("/get_info")
    ResponseEntity<?> getOrderInfo(@RequestHeader("Authorization") String token,
                                   @RequestBody OrderInfoDto requestDto);
}
