package com.project.ecom.models;

import com.project.ecom.enums.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PhoneNumber {
    @NotNull(message = "Country code is required")
    @Enumerated(EnumType.STRING)
    private Country country;
    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;
}
