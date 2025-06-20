package com.project.ecom.dtos.clients.fake_store_api;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String image;
}
