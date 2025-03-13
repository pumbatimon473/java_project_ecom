package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAddressRequestDto {
    private Long customerId;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String pinCode;
    private String city;
    private String state;
    private String country;
}
