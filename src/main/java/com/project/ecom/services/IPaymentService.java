package com.project.ecom.services;

import com.project.ecom.models.Payment;

public interface IPaymentService {
    String getPaymentLink(Long customerId, Long orderId);

    Payment getPayment(String sessionId);
}
