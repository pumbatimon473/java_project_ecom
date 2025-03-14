package com.project.ecom.dtos;

import com.project.ecom.models.ProductCategory;
import com.project.ecom.models.ProductImage;
import com.project.ecom.models.Seller;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddProductResponseDto {
    private Long productId;
    private String name;
    private ProductCategory productCategory;
    private String description;
    private BigDecimal price;
    private ProductImage image;
    private Seller seller;
}
