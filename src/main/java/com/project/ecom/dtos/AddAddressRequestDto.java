package com.project.ecom.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAddressRequestDto {
    // private Long customerId;
    @NotEmpty(message = "Address line 1 is mandatory")
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    @NotEmpty(message = "Pin Code is required")
    private String pinCode;
    @NotEmpty(message = "City is required")
    private String city;
    @NotEmpty(message = "State is required")
    private String state;
    @NotEmpty(message = "Country is required")
    private String country;
}
