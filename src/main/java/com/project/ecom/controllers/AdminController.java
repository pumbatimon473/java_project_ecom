package com.project.ecom.controllers;

import com.project.ecom.dtos.CreateProductCategoryRequestDto;
import com.project.ecom.dtos.CreateProductCategoryResponseDto;
import com.project.ecom.dtos.RegisterSellerRequestDto;
import com.project.ecom.dtos.RegisterSellerResponseDto;
import com.project.ecom.models.ProductCategory;
import com.project.ecom.models.Seller;
import com.project.ecom.services.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final IAdminService adminService;

    @Autowired
    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/product_category")
    ResponseEntity<CreateProductCategoryResponseDto> createProductCategory(@RequestBody CreateProductCategoryRequestDto requestDto, @AuthenticationPrincipal Jwt jwt) {
        Long adminId = jwt.getClaim("user_id");
        ProductCategory productCategory = this.adminService.addProductCategory(adminId, requestDto.getProductCategoryName(), requestDto.getDescription());
        CreateProductCategoryResponseDto responseDto = new CreateProductCategoryResponseDto();
        responseDto.setId(productCategory.getId());
        responseDto.setProductCategoryName(productCategory.getName());
        responseDto.setDescription(productCategory.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
