package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long cartItemId;
    private ProductInCartDto product;
    private Integer quantity;
}
