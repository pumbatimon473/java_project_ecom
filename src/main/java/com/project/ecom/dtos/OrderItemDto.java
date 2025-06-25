package com.project.ecom.dtos;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.models.OrderItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemDto {
    private ProductInCartDto product;
    private Integer quantity;

    public static OrderItemDto from(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProduct(ProductInCartDto.from(orderItem.getProduct()));
        orderItemDto.setQuantity(orderItem.getQuantity());
        return orderItemDto;
    }
}
