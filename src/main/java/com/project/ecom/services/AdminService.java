package com.project.ecom.services;

import com.project.ecom.enums.UserType;
import com.project.ecom.exceptions.UnauthorizedUserException;
import com.project.ecom.exceptions.UserAlreadyExistsException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.*;
import com.project.ecom.repositories.IAdminRepository;
import com.project.ecom.repositories.IProductCategoryRepository;
import com.project.ecom.repositories.ISellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {
    private IAdminRepository adminRepo;
    private IProductCategoryRepository productCategoryRepo;
    private ISellerRepository sellerRepo;

    @Autowired
    public AdminService(IAdminRepository adminRepo, IProductCategoryRepository productCategoryRepo, ISellerRepository sellerRepo) {
        this.adminRepo = adminRepo;
        this.productCategoryRepo = productCategoryRepo;
        this.sellerRepo = sellerRepo;
    }

    @Override
    public User registerAdmin(Long adminId, String name, String email, String password) throws UserNotFoundException, UnauthorizedUserException, UserAlreadyExistsException {
        Admin admin = this.adminRepo.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException(adminId));
        if (admin.getUserType() != UserType.ADMIN)
            throw new UnauthorizedUserException(adminId);
        if (this.adminRepo.findByEmail(email).isPresent())
            throw new UserAlreadyExistsException(email);
        // create admin
        Admin newAdmin = new Admin();
        newAdmin.setName(name);
        newAdmin.setEmail(email);
        newAdmin.setPassword(password);
        return this.adminRepo.save(newAdmin);
    }

    @Override
    public ProductCategory addProductCategory(Long adminId, String name, String description) throws UserNotFoundException, UnauthorizedUserException {
        Admin admin = this.adminRepo.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException(adminId));
        if (admin.getUserType() != UserType.ADMIN)
            throw new UnauthorizedUserException(adminId);
        // create new product category
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(name);
        productCategory.setDescription(description);
        return this.productCategoryRepo.save(productCategory);
    }

    @Override
    public Seller registerSeller(Long adminId, String name, String email, String password, PhoneNumber phoneNumber, String panNumber, String gstRegNumber) throws UserNotFoundException, UnauthorizedUserException, UserAlreadyExistsException {
        Admin admin = this.adminRepo.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException(adminId));
        if (admin.getUserType() != UserType.ADMIN)
            throw new UnauthorizedUserException(adminId);
        // check if the seller already exists
        if (this.sellerRepo.findByEmail(email).isPresent())
            throw new UserAlreadyExistsException(email);
        // create new seller
        Seller seller = new Seller();
        seller.setName(name);
        seller.setEmail(email);
        seller.setPassword(password);
        seller.setPhoneNumber(phoneNumber);
        seller.setPanNumber(panNumber);
        seller.setGstRegNumber(gstRegNumber);
        return this.sellerRepo.save(seller);
    }
}
