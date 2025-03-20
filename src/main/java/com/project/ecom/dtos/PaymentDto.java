package com.project.ecom.dtos;

import com.project.ecom.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PaymentDto {
    private List<Long> orderIds;
    private BigDecimal amount;
    private PaymentStatus status;
    private String transactionId;
}
