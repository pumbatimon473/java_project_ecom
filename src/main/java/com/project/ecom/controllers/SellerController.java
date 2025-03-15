package com.project.ecom.controllers;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.dtos.*;
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
    private final ISellerService sellerService;

    @Autowired
    public SellerController(ISellerService sellerService) {
        this.sellerService = sellerService;
    }

    /*
    Encountering error while writing the JSON response:
    {
    "errorCode": "INTERNAL_SERVER_ERROR",
    "errorMessage": "Could not write JSON: Document nesting depth (1001) exceeds the maximum allowed (1000, from `StreamWriteConstraints.getMaxNestingDepth()`)"
    }

    Root Cause: Bidirectional relation between Product and ProductCategory
    - The Product is referencing ProductCategory, which in turn is referencing Product,
    leading to circular reference creation
    - The circular reference creation could lead to infinite recursive calls during JSON serialization.

    Resolution:
    - Using @JsonIgnore or @JsonManagedReference and @JsonBackReference to manage circular reference
    during JSON serialization.
        - @JsonIgnore: ignores one side of the relation during JSON serialization
        - @JsonManagedReference and @JsonBackReference:
            - @JsonManagedReference: used on the owning side of the relation (forward relation - has the foreign key)
            - @JsonBackReference: used on the non-owning side (inverse side of the relation)
    - Using custom DTOs to control JSON serialization by having the necessary attributes.
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

        SellerDto sellerDto = Reusable.mapSellerToSellerDto(product.getSeller());
        responseDto.setSeller(sellerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/product_inventory")
    public ResponseEntity<UpdateProductInventoryResponseDto> createOrUpdateProductInventory(@RequestBody UpdateProductInventoryRequestDto requestDto) {
        ProductInventory productInventory = this.sellerService.updateProductInventory(requestDto.getSellerId(), requestDto.getProductId(), requestDto.getQuantity());
        UpdateProductInventoryResponseDto responseDto = new UpdateProductInventoryResponseDto();
        responseDto.setProductInventoryId(productInventory.getId());

        Product product = productInventory.getProduct();
        responseDto.setProduct(Reusable.mapProductToProductDto(product));
        responseDto.setQuantity(productInventory.getQuantity());
        return ResponseEntity.ok(responseDto);
    }
}
