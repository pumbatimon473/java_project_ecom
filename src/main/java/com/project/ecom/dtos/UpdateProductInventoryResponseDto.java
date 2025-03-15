package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductInventoryResponseDto {
    private Long productInventoryId;
    private ProductDto product;
    private Integer quantity;
}
