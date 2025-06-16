package com.project.ecom.dtos;

import com.project.ecom.enums.OrderStatus;
import com.project.ecom.models.Address;
import com.project.ecom.models.Order;
import com.project.ecom.models.OrderTotal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    Long orderId;
    Long customerId;
    List<OrderItemDto> orderItems;
    OrderTotal orderTotal;
    OrderStatus orderStatus;

    public static OrderDto from(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getId());
        orderDto.setCustomerId(order.getCustomerId());
        orderDto.setOrderItems(order.getOrderItems().stream().map(OrderItemDto::from).toList());
        orderDto.setOrderTotal(order.getOrderTotal());
        orderDto.setOrderStatus(order.getStatus());
        return orderDto;
    }
}
