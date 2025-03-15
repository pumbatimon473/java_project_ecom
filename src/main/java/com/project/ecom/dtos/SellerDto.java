package com.project.ecom.dtos;

import com.project.ecom.models.PhoneNumber;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDto {
    private Long sellerId;
    private String name;
    private String email;
    private PhoneNumber phoneNumber;
}
