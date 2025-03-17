package com.project.ecom.dtos;

import com.project.ecom.enums.OrderStatus;
import com.project.ecom.models.Address;
import com.project.ecom.models.OrderTotal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetailsDto {
    private Long orderId;
    private OrderStatus status;
    private Long customerId;
    private Address deliveryAddress;
    private OrderTotal orderTotal;
    private List<OrderItemDto> orderItems;
}
