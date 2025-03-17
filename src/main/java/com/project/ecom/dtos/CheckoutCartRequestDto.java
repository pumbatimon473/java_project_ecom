package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutCartRequestDto {
    private Long customerId;
    private Long deliveryAddressId;
}
