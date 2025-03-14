package com.project.ecom.exceptions;

public class ProductCategoryNotFoundException extends RuntimeException {
    public ProductCategoryNotFoundException(Long categoryId) {
        super("No product category exists with id: " + categoryId);
    }
}
