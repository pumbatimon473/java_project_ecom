package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCustomerRequestDto {
    private String name;
    private String email;
    private String password;
}
