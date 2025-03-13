package com.project.ecom.dtos;

import com.project.ecom.enums.Country;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAddressResponseDto {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String pinCode;
    private String landmark;
    private String city;
    private String state;
    private Country country;
}
