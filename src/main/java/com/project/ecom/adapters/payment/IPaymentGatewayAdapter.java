package com.project.ecom.adapters.payment;

import com.project.ecom.models.Customer;
import com.project.ecom.models.Order;
import com.project.ecom.models.PaymentTransaction;

public interface IPaymentGatewayAdapter {
    String createPaymentLink(Order order, Customer customer);

    PaymentTransaction getPaymentTransaction(String sessionId);
}
