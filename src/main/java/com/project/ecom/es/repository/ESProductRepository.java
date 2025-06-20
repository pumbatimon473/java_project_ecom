package com.project.ecom.es.repository;

import com.project.ecom.es.mapper.ESProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.math.BigDecimal;

public interface ESProductRepository extends ElasticsearchRepository<ESProduct, Long> {
    Page<ESProduct> findByNameContaining(String name, Pageable pageable);

    Page<ESProduct> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);

    Page<ESProduct> findByNameContainingAndPriceBetween(String query, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<ESProduct> findByDescriptionContainingAndPriceBetween(String query, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
