package com.project.ecom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecom.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Seller extends User {
    private String panNumber;
    private String gstRegNumber;

    private List<Invoice> invoices;

    private List<Product> products;

    @Override
    public UserType getUserType() {
        return UserType.SELLER;
    }

    private Address address;
}
