package com.project.ecom.models;

import com.project.ecom.enums.Country;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class PhoneNumber {
    @Enumerated(EnumType.STRING)
    private Country country;
    private String phoneNumber;
}
