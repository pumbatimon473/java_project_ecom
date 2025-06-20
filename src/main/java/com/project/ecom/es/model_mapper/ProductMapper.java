package com.project.ecom.es.model_mapper;

import com.project.ecom.es.mapper.ESProduct;
import com.project.ecom.models.Product;

public class ProductMapper {
    public static ESProduct mapToESProduct(Product product) {
        return ESProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory().getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .primaryImageUrl(product.getImage() != null ? product.getImage().getImageUrls().get(0) : null)
                .build();
    }
}
