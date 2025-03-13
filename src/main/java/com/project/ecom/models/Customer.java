package com.project.ecom.models;

import com.project.ecom.enums.UserType;
import jakarta.persistence.*;
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

    @ManyToMany  // separate mapping table
    @JoinTable(
            name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses;
}
