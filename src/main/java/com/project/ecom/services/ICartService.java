package com.project.ecom.services;

import com.project.ecom.models.Cart;
import com.project.ecom.models.CartItem;

import java.util.List;

public interface ICartService {
    CartItem addProductToCart(Long customerId, Long productId, Integer quantity);

    void removeCartItem(Long customerId, Long cartItemId);

    CartItem updateCartItem(Long customerId, Long cartItemId, Integer incrementVal);

    Cart getCart(Long customerId);  // returns ACTIVE cart
}
