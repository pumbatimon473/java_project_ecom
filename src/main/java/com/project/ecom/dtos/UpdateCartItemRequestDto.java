package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequestDto {
    // private Long customerId;
    private Long cartItemId;
    private Integer incrementVal;
}
