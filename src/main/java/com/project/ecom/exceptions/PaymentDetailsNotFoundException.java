package com.project.ecom.exceptions;

public class PaymentDetailsNotFoundException extends RuntimeException {
    public PaymentDetailsNotFoundException(String msg) {
        super(msg);
    }
}
