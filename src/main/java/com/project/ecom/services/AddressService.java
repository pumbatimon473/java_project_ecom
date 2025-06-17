package com.project.ecom.services;

import com.project.ecom.dtos.AddAddressRequestDto;
import com.project.ecom.enums.AddressType;
import com.project.ecom.enums.Country;
import com.project.ecom.enums.State;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Address;
import com.project.ecom.repositories.IAddressRepository;
import com.project.ecom.utils.StateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {
    private final IAddressRepository addressRepo;

    @Autowired
    public AddressService(IAddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public Address addAddress(Long userId, AddAddressRequestDto addressDetails, AddressType addressType) throws UserNotFoundException {
        // validate address
        Country _country = Country.valueOf(addressDetails.getCountry().toUpperCase().trim());
        State _state = StateConverter.getState(_country, addressDetails.getState());
        // create address
        Address address = new Address();
        address.setUserId(userId);
        address.setAddressLine1(addressDetails.getAddressLine1());
        address.setAddressLine2(addressDetails.getAddressLine2());
        address.setLandmark(addressDetails.getLandmark());
        address.setPinCode(addressDetails.getPinCode());
        address.setCity(addressDetails.getCity());
        address.setState(_state.getStateName());
        address.setCountry(_country);
        address.setAddressType(addressType);
        address = this.addressRepo.save(address);  // address with id
        // add address to the customer's profile
        return address;
    }

    @Override
    public List<Address> getAddress(Long userId, AddressType addressType) {
        return this.addressRepo.findByUserIdAndAddressType(userId, addressType);
    }
}
