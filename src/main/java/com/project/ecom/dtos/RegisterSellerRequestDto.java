package com.project.ecom.dtos;

import com.project.ecom.models.PhoneNumber;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterSellerRequestDto {
    private Long adminId;
    private String name;
    private String email;
    private String password;
    private PhoneNumber phoneNumber;
    private String panNumber;
    private String gstRegNumber;
}
