package com.project.ecom.controllers;

import com.project.ecom.dtos.*;
import com.project.ecom.exceptions.UserAlreadyExistsException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Address;
import com.project.ecom.models.User;
import com.project.ecom.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Address address = this.customerService.addAddress(
                userId, requestDto.getAddressLine1(), requestDto.getAddressLine2(),
                requestDto.getLandmark(), requestDto.getPinCode(), requestDto.getCity(),
                requestDto.getState(), requestDto.getCountry()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(AddAddressResponseDto.from(address));
    }
}
