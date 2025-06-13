package com.project.ecom.services;

import com.project.ecom.exceptions.UserAlreadyExistsException;
import com.project.ecom.exceptions.UserNotFoundException;
import com.project.ecom.models.Address;
import com.project.ecom.models.User;

public interface ICustomerService {
    Address addAddress(Long userId, String addressLine1, String addressLine2, String landmark, String pinCode, String city, String state, String country)
            throws UserNotFoundException;
}
