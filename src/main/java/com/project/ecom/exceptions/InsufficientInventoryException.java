package com.project.ecom.exceptions;

public class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException(Long productId) {
        super("Not sufficient inventory available for the product " + productId);
    }
}
