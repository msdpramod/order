package com.blinkit.order.service;

import com.blinkit.order.dtos.CartItemRequest;
import com.blinkit.order.models.CartItem;
import com.blinkit.order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;


    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        // Check product
//        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
//        if (productOpt.isEmpty()) return false;
//
//        Product product = productOpt.get();
//
//        // Check stock
//        if (product.getStockQuantity() < cartItemRequest.getQuantity()) return false;
//
//        // Check user
//        Optional<User> userOpt = userRepository.findById(Long.parseLong(id));
//        if (userOpt.isEmpty()) return false;
//
//        User user = userOpt.get();

        // Add or update cart item
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, cartItemRequest.getProductId());
        if (existingCartItem != null) {
            int newQuantity = existingCartItem.getQuantity() + cartItemRequest.getQuantity();
            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setUserId(userId);
            newCartItem.setProductId(cartItemRequest.getProductId());
            newCartItem.setQuantity(cartItemRequest.getQuantity());
            newCartItem.setPrice(
                    BigDecimal.valueOf(1000.00)
            );
            cartItemRepository.save(newCartItem);
        }

        return true;
    }
    public boolean addToCartFallBack(String userId,
                                     CartItemRequest request,
                                     Exception exception) {
        exception.printStackTrace();
        return false;
    }

    @Transactional
    public boolean removeFromCart(String userId, String productId) {


        // Find and delete cart item
        CartItem cartItem =cartItemRepository.findByUserIdAndProductId(userId,productId);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }


    public List<CartItem> getCartItems(String userId) {
        return cartItemRepository.findByUserId(userId);


    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
