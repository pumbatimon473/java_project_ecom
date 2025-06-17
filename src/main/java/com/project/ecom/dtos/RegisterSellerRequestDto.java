package com.project.ecom.dtos;

import com.project.ecom.models.Address;
import com.project.ecom.models.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterSellerRequestDto {
    @NotBlank(message = "Business name is mandatory")
    private String businessName;
    @NotBlank(message = "A valid pan number is required")
    private String panNumber;
    @NotBlank(message = "GST registration number is required for business")
    private String gstRegNumber;
    @NotNull(message = "Business contact number is required")
    @Valid
    private PhoneNumber businessContact;
    @NotNull(message = "Business address is required")
    @Valid
    private AddAddressRequestDto businessAddress;
}
