package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductToCartReqDto {
    private Long customerId;
    private Long productId;
    private Integer quantity;
}
