package com.project.ecom.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super(String.format("No order exists with id: %d", orderId));
    }
}
