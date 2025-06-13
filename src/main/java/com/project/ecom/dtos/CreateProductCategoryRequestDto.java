package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductCategoryRequestDto {
    // private Long adminId;
    private String productCategoryName;
    private String description;
}
