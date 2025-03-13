package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCustomerResponseDto {
    private Long customerId;
    private String name;
    private String email;
}
