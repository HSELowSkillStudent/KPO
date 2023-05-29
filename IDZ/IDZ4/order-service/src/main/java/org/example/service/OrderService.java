package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.repository.DishRepository;
import org.example.domain.repository.OrderRepository;
import org.example.dto.response.OrderDto;
import org.example.dto.response.OrderInfoDto;
import org.example.exception.InvalidOperationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OrderService
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;

    /**
     * Make order
     *
     * @param token    jwt token
     * @param orderDto OrderDto
     * @return Object
     * @throws InvalidOperationException if token is invalid
     */
    public Object makeOrder(String token, org.example.dto.request.OrderDto orderDto) throws InvalidOperationException {
        //  https://avatars.mds.yandex.net/i?id=c0e8ec46fe07333877dd5c444d174d4420a4b5de-4571323-images-thumbs&n=13
        return null;
    }

    /**
     * Get order list
     *
     * @param token jwt token
     * @return List<OrderDto>
     * @throws InvalidOperationException if token is invalid
     */
    public List<OrderDto> getOrderList(String token) throws InvalidOperationException {
        //  https://cdn.fishki.net/upload/post/201409/11/1303500/1379743__1.jpg
        return null;
    }

    /**
     * Change order status
     * @param token jwt token
     * @param requestDto OrderInfoDto
     * @return String
     * @throws InvalidOperationException if token is invalid
     */
    public String changeOrderStatus(String token,
                                    org.example.dto.request.OrderInfoDto requestDto) throws InvalidOperationException {
        //  https://aftershock.news/sites/default/files/u4820/teasers/aftershock_141.jpg
        return null;
    }

    /**
     * Get order info
     * @param token jwt token
     * @param requestDto OrderInfoDto
     * @return OrderInfoDto
     * @throws InvalidOperationException if token is invalid
     */
    public OrderInfoDto getOrderInfo(String token,
                                     org.example.dto.request.OrderInfoDto requestDto) throws InvalidOperationException {
        //  https://stihi.ru/pics/2020/10/22/8355.jpg
        return null;
    }
}
