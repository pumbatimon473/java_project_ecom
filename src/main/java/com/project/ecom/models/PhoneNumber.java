package com.project.ecom.models;

import com.project.ecom.enums.Country;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PhoneNumber extends BaseModel {
    @Enumerated(EnumType.STRING)
    private Country country;
    private String phoneNumber;

    private Long userId;
}
