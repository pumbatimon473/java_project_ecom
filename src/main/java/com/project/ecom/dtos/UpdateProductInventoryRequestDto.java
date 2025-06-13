package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductInventoryRequestDto {
    // private Long sellerId;
    private Long productId;
    private Integer quantity;  // represents the quantity to be added to the current inventory
}
