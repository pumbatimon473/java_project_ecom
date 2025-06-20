package com.project.ecom.es.services;

import com.project.ecom.es.mapper.ESProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IProductSearchService {
    Page<ESProduct> searchProductsByName(String name, Pageable pageable);

    Page<ESProduct> searchProducts(String query, Pageable pageable);

    Page<ESProduct> searchProductsByQueryAndPriceRange(String query, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
