package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemDetails {
    private String id;
    private Long productId;
    private String productName;
    private String primaryImageUrl;
    private Integer quantity;
    private BigDecimal priceAtAddition;
}
