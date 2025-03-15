package com.project.ecom.dtos;

import com.project.ecom.models.ProductImage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDetailsDto {
    private Long productId;
    private String name;
    private String category;
    private String description;
    private BigDecimal price;
    private ProductImage image;
    private SellerDto soldBy;
}
