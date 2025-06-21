package com.project.ecom.dtos;

import com.project.ecom.models.Product;
import com.project.ecom.models.ProductImage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class BasicProductDetailsDto {
    private Long productId;
    private String name;
    private String primaryImageUrl;
    private BigDecimal price;

    public static BasicProductDetailsDto from(Product product) {
        BasicProductDetailsDto productDto = new BasicProductDetailsDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());

        ProductImage productImage = product.getImage();
        if (productImage != null)
            productDto.setPrimaryImageUrl(productImage.getImageUrls().get(0));

        return productDto;
    }
}
