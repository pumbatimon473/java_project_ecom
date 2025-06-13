package com.project.ecom.services;

import com.project.ecom.exceptions.UnauthorizedUserException;
import com.project.ecom.exceptions.UserAlreadyExistsException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.PhoneNumber;
import com.project.ecom.models.ProductCategory;
import com.project.ecom.models.Seller;
import com.project.ecom.models.User;

public interface IAdminService {
    ProductCategory addProductCategory(Long adminId, String name, String description);

}
