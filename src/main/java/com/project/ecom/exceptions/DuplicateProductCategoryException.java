package com.project.ecom.exceptions;

public class DuplicateProductCategoryException extends RuntimeException {
    public DuplicateProductCategoryException(String name) {
        super("Duplicate product category: " + name);
    }
}
