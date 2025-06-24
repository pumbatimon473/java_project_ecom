package com.project.ecom.exceptions;

public class SellerProfileNotFoundException extends RuntimeException {
    public SellerProfileNotFoundException(Long sellerId) {
        super("Profile not found for seller id: " + sellerId);
    }
}
