package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.ecom.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class Payment extends BaseModel {
    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private Order order;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionId;

    @OneToMany(mappedBy = "payment")
    @JsonBackReference
    private List<Invoice> invoices;

    private String sessionId;  // represents the checkout session id or the payment link id
}
