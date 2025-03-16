package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemResponseDto {
    private Long cartItemId;
    private CartStatusDto cart;
    private ProductInCartDto productInCartDto;
    private Integer quantity;
}
