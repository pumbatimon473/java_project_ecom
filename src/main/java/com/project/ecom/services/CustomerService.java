package com.project.ecom.services;

import com.project.ecom.enums.Country;
import com.project.ecom.enums.State;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Address;
import com.project.ecom.repositories.IAddressRepository;
import com.project.ecom.utils.StateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
    private IAddressRepository addressRepo;

    @Autowired
    public CustomerService(IAddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public Address addAddress(Long userId, String addressLine1, String addressLine2, String landmark, String pinCode, String city, String state, String country) throws UserNotFoundException {
        // validate address
        Country _country = Country.valueOf(country.toUpperCase().trim());
        State _state = StateConverter.getState(_country, state);
        // create address
        Address address = new Address();
        address.setUserId(userId);
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setLandmark(landmark);
        address.setPinCode(pinCode);
        address.setCity(city);
        address.setState(_state.getStateName());
        address.setCountry(_country);
        address = this.addressRepo.save(address);  // address with id
        // add address to the customer's profile
        return address;
    }


}
