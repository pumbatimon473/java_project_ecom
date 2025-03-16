package com.project.ecom.exceptions;

public class NoActiveUserSessionException extends RuntimeException {
    public NoActiveUserSessionException(Long customerId) {
        super(String.format("No active session found for customer id %d", customerId));
    }
}
