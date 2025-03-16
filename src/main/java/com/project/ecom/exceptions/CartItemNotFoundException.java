package com.project.ecom.exceptions;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long cartItemId) {
        super(String.format("No cart item exists with id %d", cartItemId));
    }
}
