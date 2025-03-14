package com.project.ecom.controllers;

import com.project.ecom.dtos.AddProductRequestDto;
import com.project.ecom.dtos.AddProductResponseDto;
import com.project.ecom.dtos.UpdateProductInventoryRequestDto;
import com.project.ecom.dtos.UpdateProductInventoryResponseDto;
import com.project.ecom.models.Product;
import com.project.ecom.models.ProductInventory;
import com.project.ecom.services.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    private ISellerService sellerService;

    @Autowired
    public SellerController(ISellerService sellerService) {
        this.sellerService = sellerService;
    }

    /*
    Encountering error while writing the response:
    {
    "errorCode": "INTERNAL_SERVER_ERROR",
    "errorMessage": "Could not write JSON: Document nesting depth (1001) exceeds the maximum allowed (1000, from `StreamWriteConstraints.getMaxNestingDepth()`)"
    }

    Cause:
    - The associated product category is trying to fetch all the associated products.
    - Since, each product has associated category, this is resulting in a nested call.

    Observation:
    - Since a product category can have multiple products, the default fetch type should be LAZY
    but somehow all the associated products are being fetched.
     */
    @PostMapping("/product")
    public ResponseEntity<AddProductResponseDto> addProduct(@RequestBody AddProductRequestDto requestDto) {
        Product product = this.sellerService.addProduct(requestDto.getSellerId(), requestDto.getName(), requestDto.getProductCategoryId(), requestDto.getDescription(), requestDto.getPrice(), requestDto.getImageUrls());
        AddProductResponseDto responseDto = new AddProductResponseDto();
        responseDto.setProductId(product.getId());
        responseDto.setName(product.getName());
        responseDto.setProductCategory(product.getCategory());
        responseDto.setDescription(product.getDescription());
        responseDto.setPrice(product.getPrice());
        responseDto.setImage(product.getImage());
        responseDto.setSeller(product.getSeller());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/product_inventory")
    public ResponseEntity<UpdateProductInventoryResponseDto> createOrUpdateProductInventory(@RequestBody UpdateProductInventoryRequestDto requestDto) {
        ProductInventory productInventory = this.sellerService.updateProductInventory(requestDto.getSellerId(), requestDto.getProductId(), requestDto.getQuantity());
        UpdateProductInventoryResponseDto responseDto = new UpdateProductInventoryResponseDto();
        responseDto.setProductInventoryId(productInventory.getId());
        responseDto.setProduct(productInventory.getProduct());
        responseDto.setQuantity(productInventory.getQuantity());
        return ResponseEntity.ok(responseDto);
    }
}
