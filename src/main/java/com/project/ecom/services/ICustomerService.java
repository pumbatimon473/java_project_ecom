package com.project.ecom.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ecom.dtos.AddAddressRequestDto;
import com.project.ecom.dtos.CustomerProfileResponseDto;
import com.project.ecom.enums.AddressType;
import com.project.ecom.exceptions.UserAlreadyExistsException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Address;
import com.project.ecom.models.PhoneNumber;
import com.project.ecom.models.SellerRegistrationApplication;
import com.project.ecom.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface ICustomerService {
    Address addAddress(Long userId, AddAddressRequestDto addressDetails)
            throws UserNotFoundException;

    CustomerProfileResponseDto getProfile(Long customerId);

    SellerRegistrationApplication registerAsSeller(Long userId, @NotBlank(message = "Business name is mandatory") String businessName, @NotBlank(message = "A valid pan number is required") String panNumber, @NotBlank(message = "GST registration number is required for business") String gstRegNumber, @NotNull(message = "Business contact number is required") PhoneNumber businessContact, @NotNull(message = "Business address is required") AddAddressRequestDto businessAddress) throws JsonProcessingException;
}
