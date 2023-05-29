package org.example.controller;

import org.example.api.OrderApi;
import org.example.dto.request.OrderInfoDto;
import org.example.dto.request.OrderDto;
import org.example.service.OrderService;
import org.example.exception.InvalidOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController implements OrderApi {
    private final OrderService orderService;

    @Override
    public ResponseEntity<?> createOrder(String token, OrderDto orderDto) {
        try {
            return ResponseEntity.ok(orderService.makeOrder(token, orderDto));
        } catch (InvalidOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getOrderList(String token) {
        try {
            return ResponseEntity.ok(orderService.getOrderList(token));
        } catch (InvalidOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> changeStatusById(String token, OrderInfoDto requestDto) {
        try {
            return ResponseEntity.ok(orderService.changeOrderStatus(token, requestDto));
        } catch (InvalidOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getOrderInfo(String token, OrderInfoDto requestDto) {
        try {
            return ResponseEntity.ok(orderService.getOrderInfo(token, requestDto));
        } catch (InvalidOperationException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}
