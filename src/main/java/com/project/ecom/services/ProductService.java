package com.project.ecom.services;

import com.project.ecom.exceptions.ProductCategoryNotFoundException;
import com.project.ecom.exceptions.ProductNotFoundException;
import com.project.ecom.models.Product;
import com.project.ecom.repositories.IProductCategoryRepository;
import com.project.ecom.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    private IProductRepository productRepo;
    private IProductCategoryRepository productCategoryRepo;

    @Autowired
    public ProductService(IProductRepository productRepo, IProductCategoryRepository productCategoryRepo) {
        this.productRepo = productRepo;
        this.productCategoryRepo = productCategoryRepo;
    }

    @Override
    public Page<Product> getAllProducts(Integer pageNumber, Integer pageSize) {
        // defined in PagingAndSortingRepository
        return this.productRepo.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Product getProductById(Long productId) {
        return this.productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Page<Product> getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize) {
        this.productCategoryRepo.findById(categoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException(categoryId));
        return this.productRepo.findAllByCategoryId(categoryId, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Page<Product> searchProductsByName(String name, Integer pageNumber, Integer pageSize) {
        return this.productRepo.findAllByNameContainingIgnoreCase(name, PageRequest.of(pageNumber, pageSize));
    }
}
