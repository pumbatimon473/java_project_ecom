package com.project.ecom.services;

import com.project.ecom.dtos.SellerProfileResponseDto;
import com.project.ecom.exceptions.ProductNotFoundException;
import com.project.ecom.exceptions.UnauthorizedUserException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Product;
import com.project.ecom.models.ProductInventory;

import java.math.BigDecimal;
import java.util.List;

public interface ISellerService {
    Product addProduct(Long sellerId, String name, Long categoryId, String description, BigDecimal price, List<String> imageUrls)
            throws UserNotFoundException, UnauthorizedUserException;

    ProductInventory updateProductInventory(Long sellerId, Long productId, Integer quantity)
            throws UserNotFoundException, UnauthorizedUserException, ProductNotFoundException;

    SellerProfileResponseDto getProfile(Long sellerId);
}
