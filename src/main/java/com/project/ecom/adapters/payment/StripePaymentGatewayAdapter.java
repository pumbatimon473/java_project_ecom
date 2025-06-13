package com.project.ecom.adapters.payment;

import com.project.ecom.configurations.payment.StripeConfigurationProperties;
import com.project.ecom.enums.PaymentStatus;
import com.project.ecom.exceptions.PaymentLinkCreationException;
import com.project.ecom.exceptions.PaymentTransactionRetrievalException;
import com.project.ecom.models.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Reference Doc:
Use the API to create and manage payment links
https://docs.stripe.com/payment-links/api#post-payment-invoices

Testing: Simulate payments to test your integration
https://docs.stripe.com/testing
 */

@Component("stripePaymentGateway")
public class StripePaymentGatewayAdapter implements IPaymentGatewayAdapter {
    private final StripeConfigurationProperties config;
    @Value("${stripe.api.secret-key}")
    private String apiKey;

    @Autowired
    public StripePaymentGatewayAdapter(StripeConfigurationProperties config) {
        this.config = config;
    }

    @Override
    public String createPaymentLink(Order order, Customer customer) {
        System.out.println(":: DEBUG :: config.api.secret-key: " + config.getApi().getSecretKey());
        System.out.println(":: DEBUG :: config.payment.redirect-url: " + config.getPayment().getRedirectUrl());
        Stripe.apiKey = this.config.getApi().getSecretKey();  // retrieving through config properties
        Stripe.apiKey = this.apiKey;  // or, through field annotated with @Value
        try {
            PaymentLinkCreateParams.Builder paymentLinkParamsBuilder = PaymentLinkCreateParams.builder();
            List<PaymentLinkCreateParams.LineItem> lineItems = buildLineItems(order.getOrderItems());
            paymentLinkParamsBuilder.addAllLineItem(lineItems);
            paymentLinkParamsBuilder.setAfterCompletion(buildAfterCompletion(this.config.getPayment().getRedirectUrl()));
            Map<String, String> metaData = new HashMap<>();
            metaData.put("orderId", order.getId().toString());
            metaData.put("customerId", customer.getId().toString());
            paymentLinkParamsBuilder.putAllMetadata(metaData);
            PaymentLinkCreateParams paymentLinkParams = paymentLinkParamsBuilder.build();
            PaymentLink paymentLink = PaymentLink.create(paymentLinkParams);
            System.out.println(":: DEBUG :: paymentLink :: " + paymentLink);
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new PaymentLinkCreationException(e.getUserMessage());
        }
    }

    @Override
    public PaymentTransaction getPaymentTransaction(String sessionId) {
        System.out.println(":: DEBUG :: getPayment() :: sessionId: " + sessionId);
        Stripe.apiKey = this.config.getApi().getSecretKey();
        try {
            Session checkoutSession = Session.retrieve(sessionId);
            System.out.println(":: DEBUG :: getPayment() :: Event: " + checkoutSession);
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.setTransactionId(checkoutSession.getPaymentIntent());
            paymentTransaction.setAmount(
                    new BigDecimal(BigInteger.valueOf(checkoutSession.getAmountTotal()), 2)
            );
            paymentTransaction.setStatus(checkoutSession.getPaymentStatus().equalsIgnoreCase("paid")? PaymentStatus.SUCCESS: PaymentStatus.FAILURE);
            paymentTransaction.setOrderId(Long.valueOf(checkoutSession.getMetadata().get("orderId")));
            paymentTransaction.setCustomerId(Long.valueOf(checkoutSession.getMetadata().get("customerId")));

            return paymentTransaction;
        } catch (StripeException e) {
            throw new PaymentTransactionRetrievalException(e.getUserMessage());
        }
    }

    private static PaymentLinkCreateParams.AfterCompletion buildAfterCompletion(String redirectUrl) {
        return PaymentLinkCreateParams.AfterCompletion.builder()
                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                .setRedirect(
                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                .setUrl(redirectUrl)
                                .build()
                )
                .build();
    }

    private static List<PaymentLinkCreateParams.LineItem> buildLineItems(List<OrderItem> orderItems) throws StripeException {
        List<PaymentLinkCreateParams.LineItem> lineItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            ProductCreateParams productParams = ProductCreateParams.builder()
                    .setName(orderItem.getProduct().getName())
                    .addImage(orderItem.getProduct().getImage().getImageUrls().stream().findFirst().orElse(null))
                    .build();
            // converting price to the smallest currency unit (e.g., paisa for rupee)
            BigDecimal unitPrice = orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(100));
            PriceCreateParams pricePrams = PriceCreateParams.builder()
                    .setCurrency("inr")
                    .setProduct(Product.create(productParams).getId())
                    .setUnitAmount(unitPrice.longValueExact())
                    .build();
            PaymentLinkCreateParams.LineItem lineItem = PaymentLinkCreateParams.LineItem.builder()
                    .setPrice(Price.create(pricePrams).getId())
                    .setQuantity(Long.valueOf(orderItem.getQuantity()))
                    .build();
            lineItems.add(lineItem);
        }
        return lineItems;
    }
}
