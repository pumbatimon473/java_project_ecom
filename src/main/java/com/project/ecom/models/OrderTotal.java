package com.project.ecom.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Embeddable
public class OrderTotal {
    private BigDecimal amount;

    public BigDecimal getOrderTotal() {
        return this.amount;
    }
}
