package com.project.ecom.dtos;

import com.project.ecom.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductInventoryResponseDto {
    private Long productInventoryId;
    private Product product;
    private Integer quantity;
}
