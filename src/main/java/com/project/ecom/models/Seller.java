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
public class Seller extends User {
    private String panNumber;
    private String gstRegNumber;

    @OneToMany(mappedBy = "seller")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    @Override
    public UserType getUserType() {
        return UserType.SELLER;
    }
}
