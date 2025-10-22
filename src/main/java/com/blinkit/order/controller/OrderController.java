package com.blinkit.order.controller;

import com.blinkit.order.dtos.OrderResponseDTO;
import com.blinkit.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestHeader("X-User-ID") String userId) {
        OrderResponseDTO orderResponse = orderService.createOrder(userId);

        if (orderResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new OrderResponseDTO(null, "FAILED", "Could not create order. Cart may be empty or user not found.", null));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
}
