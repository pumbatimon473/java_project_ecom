package com.project.ecom.models;

import com.project.ecom.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class PaymentTransaction {
    private String transactionId;
    private BigDecimal amount;
    private Long orderId;
    private Long customerId;
    private PaymentStatus status;
}
