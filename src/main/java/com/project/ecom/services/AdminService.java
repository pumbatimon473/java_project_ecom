package com.project.ecom.services;

import com.project.ecom.models.*;
import com.project.ecom.repositories.IProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {
    // private IAdminRepository adminRepo;
    private IProductCategoryRepository productCategoryRepo;

    @Autowired
    public AdminService(IProductCategoryRepository productCategoryRepo) {
        this.productCategoryRepo = productCategoryRepo;
    }

    @Override
    public ProductCategory addProductCategory(Long adminId, String name, String description) {
        // create new product category
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(name);
        productCategory.setDescription(description);
        return this.productCategoryRepo.save(productCategory);
    }

}
