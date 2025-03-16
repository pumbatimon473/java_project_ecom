package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDto {
    private Long cartId;
    private CustomerSessionDto session;
    private List<CartItemDto> cartItems;
}
