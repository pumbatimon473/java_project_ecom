package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductInCartDto {
    private Long productId;
    private String name;
    private BigDecimal price;
}
