package com.project.ecom.exceptions;

public class NoRecentOrdersException extends RuntimeException {
    public NoRecentOrdersException(Long customerId) {
        super("No recent orders for the customer with id: " + customerId);
    }
}
