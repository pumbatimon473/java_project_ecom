package com.project.ecom.exceptions;

public class AddressNotLinkedException extends RuntimeException {
    public AddressNotLinkedException(Long addressId, Long customerId) {
        super(String.format("The address with id %d is not linked to the customer with id %d", addressId, customerId));
    }
}
