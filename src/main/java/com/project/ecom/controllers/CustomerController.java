package com.project.ecom.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ecom.dtos.*;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Address;
import com.project.ecom.models.SellerRegistrationApplication;
import com.project.ecom.services.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/address")
    public ResponseEntity<AddAddressResponseDto> addAddress(@RequestBody AddAddressRequestDto requestDto, @AuthenticationPrincipal Jwt jwt)
            throws UserNotFoundException {
        Long userId = jwt.getClaim("user_id");
        Address address = this.customerService.addAddress(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(AddAddressResponseDto.from(address));
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerProfileResponseDto> getProfile(@AuthenticationPrincipal Jwt jwt) {
        Long customerId = jwt.getClaim("user_id");
        CustomerProfileResponseDto customerProfile = this.customerService.getProfile(customerId);
        return ResponseEntity.ok(customerProfile);
    }

    @PostMapping("/register/seller")
    public ResponseEntity<RegisterSellerResponseDto> registerAsSeller(@Valid @RequestBody RegisterSellerRequestDto requestDto, @AuthenticationPrincipal Jwt jwt) throws JsonProcessingException {
        Long userId = jwt.getClaim("user_id");
        SellerRegistrationApplication sellerRegistrationRequest = this.customerService.registerAsSeller(userId, requestDto.getBusinessName(), requestDto.getPanNumber(), requestDto.getGstRegNumber(), requestDto.getBusinessContact(), requestDto.getBusinessAddress());
        return ResponseEntity.status(HttpStatus.OK).body(RegisterSellerResponseDto.from(sellerRegistrationRequest));
    }
}
