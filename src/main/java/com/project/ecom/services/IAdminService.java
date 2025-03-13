package com.project.ecom.services;

import com.project.ecom.exceptions.UnauthorizedUserException;
import com.project.ecom.exceptions.UserAlreadyExistsException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.PhoneNumber;
import com.project.ecom.models.ProductCategory;
import com.project.ecom.models.Seller;
import com.project.ecom.models.User;

public interface IAdminService {
    User registerAdmin(Long adminId, String name, String email, String password)
            throws UserNotFoundException, UnauthorizedUserException, UserAlreadyExistsException;

    ProductCategory addProductCategory(Long adminId, String name, String description)
            throws UserNotFoundException, UnauthorizedUserException;

    Seller registerSeller(Long adminId, String name, String email, String password, PhoneNumber phoneNumber, String panNumber, String gstRegNumber)
            throws UserNotFoundException, UnauthorizedUserException, UserAlreadyExistsException;
}
