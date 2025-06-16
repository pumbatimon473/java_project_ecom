package com.project.ecom.dtos;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.enums.OrderStatus;
import com.project.ecom.models.Address;
import com.project.ecom.models.Order;
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

    public static OrderDetailsDto from(Order order) {
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
        orderDetailsDto.setOrderId(order.getId());
        orderDetailsDto.setStatus(order.getStatus());
        orderDetailsDto.setCustomerId(order.getCustomerId());
        orderDetailsDto.setDeliveryAddress(order.getDeliveryAddress());
        orderDetailsDto.setOrderTotal(order.getOrderTotal());

        List<OrderItemDto> orderItems = order.getOrderItems().stream().map(OrderItemDto::from).toList();
        orderDetailsDto.setOrderItems(orderItems);
        return orderDetailsDto;
    }
}
