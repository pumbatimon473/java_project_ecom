package com.project.ecom.services;

import com.project.ecom.exceptions.UnauthorizedUserException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ISellerService {
    Product addProduct(Long sellerId, String name, Long categoryId, String description, BigDecimal price, List<String> imageUrls)
            throws UserNotFoundException, UnauthorizedUserException;
}
