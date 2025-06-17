package com.project.ecom.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SellerProfile extends BaseModel {
    @Column(nullable = false, unique = true)
    private Long sellerId;
    private String businessName;
    private String panNumber;
    private String gstRegNumber;
    @Embedded
    private PhoneNumber businessContact;

    // No JPA mapping, handled via query
    // private Address address;
}
