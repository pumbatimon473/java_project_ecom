package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AddProductRequestDto {
    // private Long sellerId;
    private String name;
    private Long productCategoryId;
    private String description;
    private BigDecimal price;
    private List<String> imageUrls;
}
