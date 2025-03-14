package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecom.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Seller extends User {
    private String panNumber;
    private String gstRegNumber;

    @OneToMany(mappedBy = "seller")
    @JsonIgnore  // ignores serialization of the invoices collection
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "seller")
    @JsonIgnore  // ignores serialization of the products collection
    private List<Product> products;

    @Override
    public UserType getUserType() {
        return UserType.SELLER;
    }

    @OneToOne  // Unidirectional relation
    @JoinColumn(name = "address_id")
    private Address address;
}
