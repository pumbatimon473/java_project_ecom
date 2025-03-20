package com.project.ecom.models;

import com.project.ecom.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Payment extends BaseModel {
    @OneToMany(mappedBy = "payment")
    private List<Order> orders;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionId;

    @OneToMany(mappedBy = "payment")
    private List<Invoice> invoices;

    private String sessionId;  // represents the checkout session id or the payment link id
}
