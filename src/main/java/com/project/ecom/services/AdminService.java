package com.project.ecom.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecom.dtos.AddAddressRequestDto;
import com.project.ecom.enums.AddressType;
import com.project.ecom.enums.ApprovalStatus;
import com.project.ecom.enums.ReviewStatus;
import com.project.ecom.exceptions.DuplicateProductCategoryException;
import com.project.ecom.exceptions.SellerRegApplicationNotFoundException;
import com.project.ecom.models.*;
import com.project.ecom.repositories.IAddressRepository;
import com.project.ecom.repositories.IProductCategoryRepository;
import com.project.ecom.repositories.ISellerProfileRepository;
import com.project.ecom.repositories.ISellerRegistrationApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {
    // private IAdminRepository adminRepo;
    private final IProductCategoryRepository productCategoryRepo;
    private final ISellerRegistrationApplicationRepository sellerRegistrationApplicationRepo;
    private final ISellerProfileRepository sellerProfileRepo;
    private final IAddressService addressService;

    @Autowired
    public AdminService(IProductCategoryRepository productCategoryRepo, ISellerRegistrationApplicationRepository sellerRegistrationApplicationRepo, ISellerProfileRepository sellerProfileRepo, IAddressService addressService) {
        this.productCategoryRepo = productCategoryRepo;
        this.sellerRegistrationApplicationRepo = sellerRegistrationApplicationRepo;
        this.sellerProfileRepo = sellerProfileRepo;
        this.addressService = addressService;
    }

    @Override
    public ProductCategory addProductCategory(Long adminId, String name, String description) {
        Optional<ProductCategory> productCategoryOptional = this.productCategoryRepo.findByNameIgnoreCase(name.trim());
        productCategoryOptional.ifPresent(productCategory -> {
            throw new DuplicateProductCategoryException(name);
        });
        // create new product category
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(name.trim());
        productCategory.setDescription(description);
        return this.productCategoryRepo.save(productCategory);
    }

    @Override
    @Transactional
    public SellerRegistrationApplication updateSellerRegApplication(Long adminId, Long applicationId, ApprovalStatus status) throws JsonProcessingException, IllegalAccessException {
        SellerRegistrationApplication sellerRegApp = this.sellerRegistrationApplicationRepo.findById(applicationId)
                .orElseThrow(() -> new SellerRegApplicationNotFoundException(applicationId));

        if (sellerRegApp.getReviewStatus() == ReviewStatus.DONE)
            throw new IllegalAccessException("The application has already been reviewed!");

        if (status == ApprovalStatus.APPROVED) {
            Optional<SellerProfile> sellerProfileOptional = this.sellerProfileRepo.findBySellerId(sellerRegApp.getUserId());
            if (sellerProfileOptional.isEmpty()) {
                SellerProfile sellerProfile = new SellerProfile();
                sellerProfile.setSellerId(sellerRegApp.getUserId());
                sellerProfile.setBusinessName(sellerRegApp.getBusinessName());
                sellerProfile.setPanNumber(sellerRegApp.getPanNumber());
                sellerProfile.setGstRegNumber(sellerRegApp.getGstRegNumber());
                sellerProfile.setBusinessContact(sellerRegApp.getBusinessContact());
                this.sellerProfileRepo.save(sellerProfile);
            }

            // Deserialize the business address
            AddAddressRequestDto businessAddress = new ObjectMapper().readValue(sellerRegApp.getBusinessAddress(), AddAddressRequestDto.class);
            this.addressService.addAddress(sellerRegApp.getUserId(), businessAddress, AddressType.BUSINESS);
        }
        sellerRegApp.setApprovalStatus(status);
        sellerRegApp.setReviewStatus(ReviewStatus.DONE);
        return this.sellerRegistrationApplicationRepo.save(sellerRegApp);
    }

}
