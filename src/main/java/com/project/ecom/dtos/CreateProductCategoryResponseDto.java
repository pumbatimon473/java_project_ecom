package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductCategoryResponseDto {
    private Long id;
    private String productCategoryName;
    private String description;
}
