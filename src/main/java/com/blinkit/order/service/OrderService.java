package com.blinkit.order.service;

import com.blinkit.order.dtos.OrderItemDTO;
import com.blinkit.order.dtos.OrderResponseDTO;
import com.blinkit.order.models.CartItem;
import com.blinkit.order.models.Order;
import com.blinkit.order.models.OrderItem;
import com.blinkit.order.models.OrderStatus;
import com.blinkit.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CartItemService cartItemService;
    private final OrderRepository orderRepository;


    public OrderService(CartItemService cartItemService, OrderRepository orderRepository) {
        this.cartItemService = cartItemService;
        this.orderRepository = orderRepository;
    }

    public OrderResponseDTO createOrder(String userId) {
        // Validate user and cart items
        List<CartItem> cartItems = cartItemService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            return null;
        }

//        Optional<User> userOpt = userRepository.findById(Long.parseLong(userId));
//        if (userOpt.isEmpty()) {
//            return null;
//        }
//
//        User user = userOpt.get();

        // Calculate total
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalAmount = totalAmount.add(cartItem.getPrice());
        }

        // Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(totalAmount);
        order.setStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // Clear the cart
        cartItemService.clearCart(userId);

        return mapToOrderResponse(savedOrder);
    }

    private OrderResponseDTO mapToOrderResponse(Order order) {
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemDTO orderItemDTO = new OrderItemDTO(
                    orderItem.getId(),
                    orderItem.getProductId(),
                    orderItem.getQuantity(),
                    orderItem.getPrice(),
                    orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
            );
            orderItemDTOs.add(orderItemDTO);
        }

        return new OrderResponseDTO(
                order.getId(),
                order.getStatus().toString(),
                "Order placed successfully",
                orderItemDTOs
        );
    }
}
