package com.project.ecom.kafka.events;

import com.project.ecom.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderCreatedEvent {
    private Long orderId;
    private Long customerId;
    private List<Long> products;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private OrderStatus status;
}
