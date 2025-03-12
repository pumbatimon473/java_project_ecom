package com.project.ecom.models;

import com.project.ecom.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)  // defines how inheritance hierarchy should be mapped to RDBMS (Separate table for each User subtype)
public abstract class User extends BaseModel {
    private String name;
    private String email;
    private String password;

    @Embedded
    private PhoneNumber phoneNumber;

    /*
    Decoupled Address from Customer and Seller to resolve OCP (Open-Closed Principle) violation.
    - For each new User subtype created, the Address class needs to be modified resulting in OCP violation
     */
    @ManyToMany
    @JoinTable(
            name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses;

    public abstract UserType getUserType();
}
