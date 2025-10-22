package com.blinkit.order.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private String status;
    private String message;
    private List<OrderItemDTO> items;
}
