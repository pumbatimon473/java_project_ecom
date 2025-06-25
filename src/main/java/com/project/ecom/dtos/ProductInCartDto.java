package com.project.ecom.dtos;

import com.project.ecom.models.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductInCartDto {
    private Long productId;
    private String name;
    private BigDecimal price;

    public static ProductInCartDto from(Product product) {
        ProductInCartDto productDto = new ProductInCartDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
