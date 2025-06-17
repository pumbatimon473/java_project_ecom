package com.project.ecom.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecom.clients.AuthClient;
import com.project.ecom.dtos.AddAddressRequestDto;
import com.project.ecom.dtos.CustomerProfileResponseDto;
import com.project.ecom.dtos.clients.auth_client.UserInfoDto;
import com.project.ecom.enums.*;
import com.project.ecom.exceptions.SimilarRequestException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Address;
import com.project.ecom.models.CustomerProfile;
import com.project.ecom.models.PhoneNumber;
import com.project.ecom.models.SellerRegistrationApplication;
import com.project.ecom.repositories.ICustomerProfileRepository;
import com.project.ecom.repositories.ISellerRegistrationApplicationRepository;
import com.project.ecom.utils.StateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    private final IAddressService addressService;
    private final ICustomerProfileRepository customerProfileRepo;
    private final AuthClient authClient;
    private final ISellerRegistrationApplicationRepository sellerRegistrationApplicationRepo;

    @Autowired
    public CustomerService(IAddressService addressService, ICustomerProfileRepository customerProfileRepo, AuthClient authClient, ISellerRegistrationApplicationRepository sellerRegistrationApplicationRepo) {
        this.addressService = addressService;
        this.customerProfileRepo = customerProfileRepo;
        this.authClient = authClient;
        this.sellerRegistrationApplicationRepo = sellerRegistrationApplicationRepo;
    }

    @Override
    public Address addAddress(Long userId, AddAddressRequestDto addressDetails) throws UserNotFoundException {
        return this.addressService.addAddress(userId, addressDetails, AddressType.DELIVERY);
    }

    @Override
    public CustomerProfileResponseDto getProfile(Long customerId) {
        CustomerProfile customerProfile = this.customerProfileRepo.findByCustomerId(customerId)
                .orElse(null);
        List<Address> addresses = this.addressService.getAddress(customerId, AddressType.DELIVERY);
        UserInfoDto customerInfo = this.authClient.getUserInfo(customerId);
        return CustomerProfileResponseDto.from(customerInfo, customerProfile, addresses);
    }

    @Override
    public SellerRegistrationApplication registerAsSeller(Long userId, String businessName, String panNumber, String gstRegNumber, PhoneNumber businessContact, AddAddressRequestDto businessAddress) throws JsonProcessingException {
        // Assumption a user can make at most 1 request for a business
        Optional<SellerRegistrationApplication> registrationApplicationOptional = this.sellerRegistrationApplicationRepo
                .findByUserIdAndBusinessNameIgnoreCase(userId, businessName.trim());
        registrationApplicationOptional.ifPresent(regApp -> {
            throw new SimilarRequestException("A similar request already exists!");
        });
        // Create a new request
        SellerRegistrationApplication regApp = new SellerRegistrationApplication();
        regApp.setUserId(userId);
        regApp.setBusinessName(businessName);
        regApp.setPanNumber(panNumber);
        regApp.setGstRegNumber(gstRegNumber);
        regApp.setBusinessContact(businessContact);
        // serializing address as JSON str
        String jsonSerializedBusinessAddr = new ObjectMapper().writeValueAsString(businessAddress);
        regApp.setBusinessAddress(jsonSerializedBusinessAddr);
        regApp.setApprovalStatus(ApprovalStatus.PENDING);
        regApp.setReviewStatus(ReviewStatus.IN_PROGRESS);

        return this.sellerRegistrationApplicationRepo.save(regApp);
    }

}
