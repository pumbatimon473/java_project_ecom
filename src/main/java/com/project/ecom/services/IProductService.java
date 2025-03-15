package com.project.ecom.services;

import com.project.ecom.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    Page<Product> getAllProducts(Integer pageNumber, Integer pageSize);

    Product getProductById(Long productId);

    Page<Product> getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize);

    Page<Product> searchProductsByName(String name, Integer pageNumber, Integer pageSize);
}
