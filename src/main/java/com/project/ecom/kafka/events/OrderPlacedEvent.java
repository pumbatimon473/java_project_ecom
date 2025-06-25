package com.project.ecom.kafka.events;

import com.project.ecom.dtos.OrderItemDto;
import com.project.ecom.dtos.clients.auth_client.UserInfoDto;
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
public class OrderPlacedEvent {
    private Long orderId;
    private UserInfoDto customerInfo;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private String transactionId;
    private List<OrderItemDto> orderItems;
}
