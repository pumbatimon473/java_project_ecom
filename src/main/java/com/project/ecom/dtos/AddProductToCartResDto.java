package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductToCartResDto {
    private Long cartItemId;
    private Long cartId;
    private ProductInCartDto product;
    private Integer quantity;
}
