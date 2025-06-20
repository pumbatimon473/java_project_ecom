package com.project.ecom.clients.fake_store_api;

import com.project.ecom.dtos.clients.fake_store_api.FakeStoreProductDto;
import com.project.ecom.exceptions.DuplicateProductCategoryException;
import com.project.ecom.models.Product;
import com.project.ecom.models.ProductCategory;
import com.project.ecom.repositories.IProductCategoryRepository;
import com.project.ecom.repositories.IProductRepository;
import com.project.ecom.services.IAdminService;
import com.project.ecom.services.ISellerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class FakeStoreApiClientTest {
    @Autowired
    private FakeStoreApiClient apiClient;
    @Autowired
    private IProductCategoryRepository productCategoryRepo;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IProductRepository productRepo;
    @Autowired
    private ISellerService sellerService;

    @Test
    public void testFetchAllProducts() {
        List<FakeStoreProductDto> allProducts = apiClient.getAllProducts();
        System.out.println(":: TEST LOG :: FakeStoreApi :: getAllProducts ::");
        System.out.println(":: Total Products :: " + allProducts.size());
    }

    @Test
    public void testFetchAndStoreProducts() {
        List<FakeStoreProductDto> allProducts = apiClient.getAllProducts();
        Set<String> productCategories = allProducts.stream().map(FakeStoreProductDto::getCategory).collect(Collectors.toSet());
        // create product categories
        Map<String, Long> categoryToId = new HashMap<>();
        for (String productCategory : productCategories) {
            try {
                ProductCategory savedProductCategory = adminService.addProductCategory(2L, productCategory, "From FakeStoreApi");
                categoryToId.put(productCategory, savedProductCategory.getId());
            } catch (DuplicateProductCategoryException e) {
                Optional<ProductCategory> productCategoryOptional = productCategoryRepo.findByNameIgnoreCase(productCategory);
                productCategoryOptional.ifPresent(category -> categoryToId.put(productCategory, category.getId()));
            }
        }
        // create products
        for (FakeStoreProductDto fakeStoreProduct : allProducts) {
            Optional<Product> productOptional = this.productRepo.findByNameIgnoreCase(fakeStoreProduct.getTitle());
            if (productOptional.isEmpty()) {
                sellerService.addProduct(1L,
                        fakeStoreProduct.getTitle(),
                        categoryToId.get(fakeStoreProduct.getCategory()),
                        fakeStoreProduct.getDescription(),
                        fakeStoreProduct.getPrice(),
                        List.of(fakeStoreProduct.getImage())
                        );
            }
        }
    }
}
