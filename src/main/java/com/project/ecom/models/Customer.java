package com.project.ecom.models;

import com.project.ecom.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class Customer extends User {
    private List<CustomerSession> sessions;

    private List<Order> orders;

    @Override
    public UserType getUserType() {
        return UserType.CUSTOMER;
    }

    private List<Address> addresses;
}
