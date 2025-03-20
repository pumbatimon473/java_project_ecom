package com.project.ecom.adapters.payment;

import com.project.ecom.configurations.payment.RazorpayConfigurationProperties;
import com.project.ecom.enums.PaymentStatus;
import com.project.ecom.exceptions.PaymentLinkCreationException;
import com.project.ecom.exceptions.PaymentTransactionRetrievalException;
import com.project.ecom.models.Customer;
import com.project.ecom.models.Order;
import com.project.ecom.models.PaymentTransaction;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component("razorpayPaymentGateway")
public class RazorpayPaymentGatewayAdaptor implements IPaymentGatewayAdapter {
    private final RazorpayConfigurationProperties config;

    public RazorpayPaymentGatewayAdaptor(RazorpayConfigurationProperties config) {
        this.config = config;
    }

    @Override
    public String createPaymentLink(Order order, Customer customer) {
        try {
            System.out.println(":: DEBUG :: razorpay :: key_id: " + this.config.getApi().getKeyId());
            System.out.println(":: DEBUG :: razorpay :: key_secret: " + this.config.getApi().getKeySecret());
            RazorpayClient razorpayClient = new RazorpayClient(this.config.getApi().getKeyId(), this.config.getApi().getKeySecret());
            JSONObject paymentLinkReq = new JSONObject();
            // amount in the smallest current unit (e.g., for INR in Paisa)
            BigDecimal unitAmount = order.getOrderTotal().getOrderTotal().multiply(BigDecimal.valueOf(100));
            paymentLinkReq.put("amount", unitAmount.longValueExact());
            paymentLinkReq.put("currency", "INR");
            paymentLinkReq.put("description", "Payment for order id: " + order.getId());
            // customer details
            JSONObject customerObj = new JSONObject();
            customerObj.put("name", customer.getName());
            customerObj.put("email", customer.getEmail());
            paymentLinkReq.put("customer", customerObj);
            // metadata
            JSONObject notes = new JSONObject();
            notes.put("orderId", order.getId());
            notes.put("customerId", customer.getId());
            paymentLinkReq.put("notes", notes);
            // callback
            paymentLinkReq.put("callback_url", this.config.getPayment().getRedirectUrl());
            paymentLinkReq.put("callback_method", "get");

            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkReq);
            System.out.println(":: DEBUG :: Razorpay PaymentLink :: " + paymentLink);
            return (String) paymentLink.get("short_url");
        } catch (RazorpayException e) {
            throw new PaymentLinkCreationException(e.getMessage());
        }
    }

    @Override
    public PaymentTransaction getPaymentTransaction(String sessionId) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(this.config.getApi().getKeyId(), this.config.getApi().getKeySecret());
            PaymentLink paymentLink = razorpayClient.paymentLink.fetch(sessionId);
            JSONArray payments = (JSONArray) paymentLink.get("payments");
            JSONObject payment = (JSONObject) payments.get(0);
            JSONObject notes = (JSONObject) paymentLink.get("notes");
            String status = (String) paymentLink.get("status");

            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.setStatus(status.equalsIgnoreCase("paid")? PaymentStatus.SUCCESS: PaymentStatus.FAILURE);
            BigDecimal amountPaid = new BigDecimal(BigInteger.valueOf(Long.parseLong(payment.get("amount").toString())), 2);
            paymentTransaction.setAmount(amountPaid);
            paymentTransaction.setTransactionId(payment.get("payment_id").toString());
            paymentTransaction.setOrderId(Long.valueOf(notes.get("orderId").toString()));
            paymentTransaction.setCustomerId(Long.valueOf(notes.get("customerId").toString()));
            System.out.println(":: DEBUG :: razorpay transaction :: " + paymentTransaction);
            return paymentTransaction;
        } catch (RazorpayException e) {
            throw new PaymentTransactionRetrievalException(e.getMessage());
        }
    }
}
