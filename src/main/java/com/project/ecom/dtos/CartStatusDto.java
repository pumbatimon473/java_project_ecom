package com.project.ecom.dtos;

import com.project.ecom.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartStatusDto {
    private Long cartId;
    private CartStatus status;
}
