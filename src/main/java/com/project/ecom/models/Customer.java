package com.project.ecom.models;

import com.project.ecom.enums.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private List<CustomerSession> sessions;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @Override
    public UserType getUserType() {
        return UserType.CUSTOMER;
    }
}
