package com.project.ecom.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequestDto {
    // private Long customerId;
    @NotNull
    private List<CartItemDetails> cartItems;
    @NotNull
    private Long deliveryAddressId;
}
