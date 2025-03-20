package com.project.ecom.exceptions;

public class OrderConfirmationException extends RuntimeException {
    public OrderConfirmationException(String transactionId) {
        super("Order cannot be placed due to insufficient inventory of one or more order items. Amount will be refunded to original form of payment. Transaction Id: " + transactionId);
    }
}
