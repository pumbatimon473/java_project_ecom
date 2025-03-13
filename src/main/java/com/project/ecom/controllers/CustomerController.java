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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterCustomerResponseDto> registerUser(@RequestBody RegisterCustomerRequestDto requestDto)
            throws UserAlreadyExistsException {
        RegisterCustomerResponseDto responseDto = new RegisterCustomerResponseDto();
        User user = this.customerService.registerCustomer(
                requestDto.getName(), requestDto.getEmail(), requestDto.getPassword()
        );
        responseDto.setCustomerId(user.getId());
        responseDto.setName(user.getName());
        responseDto.setEmail(user.getEmail());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/address")
    public ResponseEntity<AddAddressResponseDto> addAddress(@RequestBody AddAddressRequestDto requestDto)
            throws UserNotFoundException {
        Address address = this.customerService.addAddress(
                requestDto.getCustomerId(), requestDto.getAddressLine1(), requestDto.getAddressLine2(),
                requestDto.getLandmark(), requestDto.getPinCode(), requestDto.getCity(),
                requestDto.getState(), requestDto.getCountry()
        );
        AddAddressResponseDto responseDto = new AddAddressResponseDto();
        responseDto.setId(address.getId());
        responseDto.setAddressLine1(address.getAddressLine1());
        responseDto.setAddressLine2(address.getAddressLine2());
        responseDto.setLandmark(address.getLandmark());
        responseDto.setPinCode(address.getPinCode());
        responseDto.setCity(address.getCity());
        responseDto.setState(address.getState());
        responseDto.setCountry(address.getCountry());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
