package com.project.ecom.exceptions;

public class PaymentLinkCreationException extends RuntimeException {
    public PaymentLinkCreationException(String userMessage) {
        super(userMessage);
    }
}
