package com.project.ecom.controllers;

import com.project.ecom.dtos.CheckoutCartRequestDto;
import com.project.ecom.dtos.CheckoutRequestDto;
import com.project.ecom.dtos.OrderDetailsDto;
import com.project.ecom.models.Order;
import com.project.ecom.services.ICartCheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartCheckoutController {
    private final ICartCheckoutService cartCheckoutService;

    @Autowired
    public CartCheckoutController(ICartCheckoutService cartCheckoutService) {
        this.cartCheckoutService = cartCheckoutService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Long> checkoutCart(@RequestBody CheckoutRequestDto requestDto, @AuthenticationPrincipal Jwt jwt) {
        Long customerId = jwt.getClaim("user_id");
        Order order = this.cartCheckoutService.checkout(customerId, requestDto.getCartItems(), requestDto.getDeliveryAddressId());
        return ResponseEntity.status(HttpStatus.CREATED).body(order.getId());
    }
}
