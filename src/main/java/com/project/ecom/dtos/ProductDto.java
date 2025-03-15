package com.project.ecom.dtos;

import com.project.ecom.models.ProductImage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private Long productId;
    private String name;
    private String category;
    private String description;
    private BigDecimal price;
    private String primaryImage;  // represents the first image of the product in the list
    private String soldBy;
}
