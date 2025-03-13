package com.project.ecom.services;

import com.project.ecom.enums.Country;
import com.project.ecom.enums.State;
import com.project.ecom.exceptions.UserAlreadyExistsException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Address;
import com.project.ecom.models.Customer;
import com.project.ecom.models.User;
import com.project.ecom.repositories.IAddressRepository;
import com.project.ecom.repositories.ICustomerRepository;
import com.project.ecom.utils.StateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
    private ICustomerRepository customerRepo;
    private IAddressRepository addressRepo;

    @Autowired
    public CustomerService(ICustomerRepository customerRepo, IAddressRepository addressRepo) {
        this.customerRepo = customerRepo;
        this.addressRepo = addressRepo;
    }

    @Override
    public User registerCustomer(String name, String email, String password) throws UserAlreadyExistsException {
        if (this.customerRepo.findByEmail(email).isPresent())
            throw new UserAlreadyExistsException(email);
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        return this.customerRepo.save(customer);
    }

    @Override
    public Address addAddress(Long customerId, String addressLine1, String addressLine2, String landmark, String pinCode, String city, String state, String country) throws UserNotFoundException {
        Customer customer = this.customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException(customerId));
        // validate address
        Country _country = Country.valueOf(country.toUpperCase().trim());
        State _state = StateConverter.getState(_country, state);
        // create address
        Address address = new Address();
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setLandmark(landmark);
        address.setPinCode(pinCode);
        address.setCity(city);
        address.setState(_state.getStateName());
        address.setCountry(_country);
        address = this.addressRepo.save(address);  // address with id
        // add address to the customer's profile
        customer.getAddresses().add(address);
        this.customerRepo.save(customer);
        return address;
    }


}
