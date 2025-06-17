package com.project.ecom.exceptions;

public class SellerRegApplicationNotFoundException extends RuntimeException {
    public SellerRegApplicationNotFoundException(Long applicationId) {
        super(String.format("No seller registration application found with id %d", applicationId));
    }
}
