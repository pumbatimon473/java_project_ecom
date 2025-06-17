package com.project.ecom.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CustomerProfile extends BaseModel {
    @Column(nullable = false, unique = true)
    private Long customerId;
    @Embedded
    private PhoneNumber phoneNumber;

    // No JPA mapping, handled via query
    // private List<Address> addresses;
}
