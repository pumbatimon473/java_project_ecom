package com.project.ecom.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ecom.dtos.*;
import com.project.ecom.models.ProductCategory;
import com.project.ecom.models.Seller;
import com.project.ecom.models.SellerRegistrationApplication;
import com.project.ecom.services.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final IAdminService adminService;

    @Autowired
    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/product_category")
    public ResponseEntity<CreateProductCategoryResponseDto> createProductCategory(@RequestBody CreateProductCategoryRequestDto requestDto, @AuthenticationPrincipal Jwt jwt) {
        Long adminId = jwt.getClaim("user_id");
        ProductCategory productCategory = this.adminService.addProductCategory(adminId, requestDto.getProductCategoryName(), requestDto.getDescription());
        CreateProductCategoryResponseDto responseDto = new CreateProductCategoryResponseDto();
        responseDto.setId(productCategory.getId());
        responseDto.setProductCategoryName(productCategory.getName());
        responseDto.setDescription(productCategory.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PatchMapping("/request/seller")
    public ResponseEntity<UpdateSellerRegAppResponseDto> updateSellerRegApplication(@RequestBody UpdateSellerRegistrationApplicationDto requestDto, @AuthenticationPrincipal Jwt jwt) throws JsonProcessingException, IllegalAccessException {
        Long adminId = jwt.getClaim("user_id");
        SellerRegistrationApplication sellerRegApplication = this.adminService.updateSellerRegApplication(adminId, requestDto.getApplicationId(), requestDto.getStatus());
        return ResponseEntity.ok(UpdateSellerRegAppResponseDto.from(sellerRegApplication));
    }
}
