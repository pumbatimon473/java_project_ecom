package com.project.ecom.es.controllers;

import com.project.ecom.es.dto.SearchProductsResponseDto;
import com.project.ecom.es.mapper.ESProduct;
import com.project.ecom.es.services.IProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/search/products")
public class ProductSearchController {
    private final IProductSearchService esProductSearchService;

    @Autowired
    public ProductSearchController(IProductSearchService esProductSearchService) {
        this.esProductSearchService = esProductSearchService;
    }

    @GetMapping
    ResponseEntity<SearchProductsResponseDto> searchProducts(
            @RequestParam(name = "query") String query,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        Page<ESProduct> page = this.esProductSearchService.searchProducts(query, pageable);
        return ResponseEntity.ok(SearchProductsResponseDto.from(page));
    }

    @GetMapping("/by-name")
    ResponseEntity<SearchProductsResponseDto> searchProductsByName(
            @RequestParam(name = "name") String name,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        Page<ESProduct> page = esProductSearchService.searchProductsByName(name, pageable);
        return ResponseEntity.ok(SearchProductsResponseDto.from(page));
    }

    @GetMapping("/by-name-price")
    ResponseEntity<SearchProductsResponseDto> searchByNamePrice(
            @RequestParam(name = "query") String name,
            @RequestParam(name = "min_price") BigDecimal minPrice,
            @RequestParam(name = "max_price") BigDecimal maxPrice,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        System.out.println(":: DEBUG LOG :: minPrice: " + minPrice + " | maxPrice: " + maxPrice);
        Page<ESProduct> page = this.esProductSearchService.searchProductsByQueryAndPriceRange(name, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(SearchProductsResponseDto.from(page));
    }
}
