package com.project.ecom.kafka.events;

import com.project.ecom.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderPlacedEvent {
    private Long orderId;
    private Long customerId;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private String transactionId;
}
