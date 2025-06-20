package com.project.ecom.es.services;

import com.project.ecom.es.mapper.ESProduct;
import com.project.ecom.es.repository.ESProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ESRepositoryProductSearch implements IProductSearchService {
    private final ESProductRepository esProductRepo;

    @Autowired
    public ESRepositoryProductSearch(ESProductRepository esProductRepo) {
        this.esProductRepo = esProductRepo;
    }

    @Override
    public Page<ESProduct> searchProductsByName(String name, Pageable pageable) {
        try {
            return this.esProductRepo.findByNameContaining(name, pageable);
        } catch (UncategorizedElasticsearchException e) {
            return Page.empty(pageable);
        }
    }

    @Override
    public Page<ESProduct> searchProducts(String query, Pageable pageable) {
        try {
            return this.esProductRepo.findByNameContainingOrDescriptionContaining(query, query, pageable);
        } catch (UncategorizedElasticsearchException e) {
            return Page.empty(pageable);
        }
    }

    @Override
    public Page<ESProduct> searchProductsByQueryAndPriceRange(String query, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        // Derived Query Method has its limitations: Two Ors cannot be combined with And in the desired manner due to higher precedence of And
        // Either search by name and price range
        try {
            return this.esProductRepo.findByNameContainingAndPriceBetween(query, minPrice, maxPrice, pageable);
        } catch (UncategorizedElasticsearchException e) {
            return Page.empty(pageable);
        }

        // OR, search by description and price range
        /*
        try {
            return this.esProductRepo.findByDescriptionContainingAndPriceBetween(query, minPrice, maxPrice, pageable);
        } catch (UncategorizedElasticsearchException e) {
            return Page.empty(pageable);
        }
         */

        // Third Option: Using @Query with custom ES DSL(Domain Specific Language), which is quite complex

        // Best Option: Use ElasticsearchOperations
    }
}
