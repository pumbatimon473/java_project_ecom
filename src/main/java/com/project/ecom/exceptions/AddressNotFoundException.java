package com.project.ecom.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long addressId) {
        super(String.format("No address exists with id: %d", addressId));
    }
}
