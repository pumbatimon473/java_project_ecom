package com.project.ecom.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("No product exists with id: " + productId);
    }
}
