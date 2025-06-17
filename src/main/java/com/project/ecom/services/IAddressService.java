package com.project.ecom.services;

import com.project.ecom.dtos.AddAddressRequestDto;
import com.project.ecom.enums.AddressType;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Address;

import java.util.List;

public interface IAddressService {
    Address addAddress(Long userId, AddAddressRequestDto addressDetails, AddressType addressType)
            throws UserNotFoundException;

    List<Address> getAddress(Long userId, AddressType addressType);
}
