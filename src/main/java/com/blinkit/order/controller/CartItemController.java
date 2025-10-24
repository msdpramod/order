package com.blinkit.order.controller;

import com.blinkit.order.dtos.CartItemRequest;
import com.blinkit.order.models.CartItem;
import com.blinkit.order.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

//   // public CartItemController(CartItemService cartItemService) {
//        this.cartItemService = cartItemService;
//    }

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest) {

        boolean success = cartItemService.addToCart(userId, cartItemRequest);
        if (!success) {
            return ResponseEntity.badRequest().body("Product not available or user not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to cart successfully");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable String productId) {

        boolean success = cartItemService.removeFromCart(userId, productId);
        if (!success) {
            return ResponseEntity.badRequest().body("Product not found in cart or user not found");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item removed successfully");
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(
            @RequestHeader("X-User-ID") String userId) {
        return  new ResponseEntity<>(cartItemService.getCartItems(userId), HttpStatus.OK);
    }
}
