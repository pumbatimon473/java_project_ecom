package com.project.ecom.exceptions;

public class ProductNotInInventoryException extends RuntimeException {
    public ProductNotInInventoryException(Long productId) {
        super(String.format("The specified product with id %d is not in the inventory", productId));
    }
}
