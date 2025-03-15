package com.project.ecom.controllers;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.dtos.GetProductsResponseDto;
import com.project.ecom.dtos.ProductDetailsDto;
import com.project.ecom.models.Product;
import com.project.ecom.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<GetProductsResponseDto> getAllProducts(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Product> page = this.productService.getAllProducts(pageNumber, pageSize);
        GetProductsResponseDto responseDto = Reusable.mapPageToGetProductsResponseDto(page);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDto> getProduct(@PathVariable(name = "id") Long productId) {
        Product product = this.productService.getProductById(productId);
        ProductDetailsDto productDto = Reusable.mapProductToProductDetailsDto(product);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/category/search")
    public ResponseEntity<GetProductsResponseDto> getProductsByCategory(@RequestParam(name = "categoryId") Long categoryId, @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Product> page = this.productService.getProductsByCategory(categoryId, pageNumber, pageSize);
        return ResponseEntity.ok(Reusable.mapPageToGetProductsResponseDto(page));
    }

    @GetMapping("/search")
    public ResponseEntity<GetProductsResponseDto> searchProductsByName(@RequestParam(name = "productName") String name, @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Product> page = this.productService.searchProductsByName(name, pageNumber, pageSize);
        return ResponseEntity.ok(Reusable.mapPageToGetProductsResponseDto(page));
    }
}
