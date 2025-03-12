package com.project.ecom.models;

import com.project.ecom.enums.Country;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Address extends BaseModel {
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String pinCode;
    private String city;
    private String state;  // mapping will be handled in the app logic

    @OneToMany(mappedBy = "deliveryAddress")
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    private Country country;
}
