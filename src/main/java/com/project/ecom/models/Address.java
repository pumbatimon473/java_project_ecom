package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.ecom.enums.AddressType;
import com.project.ecom.enums.Country;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Address extends BaseModel {
    @Column(nullable = false)
    private Long userId;  // could refer either customerId or sellerId

    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String pinCode;
    private String city;
    private String state;  // mapping will be handled in the app logic

    @OneToMany(mappedBy = "deliveryAddress")
    @JsonBackReference
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
}
