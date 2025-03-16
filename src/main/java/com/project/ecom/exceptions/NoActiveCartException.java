package com.project.ecom.exceptions;

public class NoActiveCartException extends RuntimeException {
    public NoActiveCartException(Long customerId) {
        super(String.format("No active cart found for customer id %d", customerId));
    }
}
