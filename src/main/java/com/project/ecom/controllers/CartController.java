package com.project.ecom.controllers;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.dtos.*;
import com.project.ecom.models.*;
import com.project.ecom.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final ICartService cartService;

    @Autowired
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<AddProductToCartResDto> addProductToCart(@RequestBody AddProductToCartReqDto requestDto, @AuthenticationPrincipal Jwt jwt) {
        Long customerId = jwt.getClaim("user_id");
        CartItem cartItem = this.cartService.addProductToCart(customerId, requestDto.getProductId(), requestDto.getQuantity());
        AddProductToCartResDto responseDto = new AddProductToCartResDto();
        responseDto.setCartItemId(cartItem.getId());
        responseDto.setCartId(cartItem.getCart().getId());

        Product product = cartItem.getProduct();
        ProductInCartDto productDto = Reusable.mapProductToProductInCartDto(product);
        responseDto.setProduct(productDto);
        responseDto.setQuantity(cartItem.getQuantity());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal Jwt jwt) {
        Long customerId = jwt.getClaim("user_id");
        Cart cart = this.cartService.getCart(customerId);
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());

        CustomerSession session = cart.getSession();
        CustomerSessionDto sessionDto = new CustomerSessionDto();
        sessionDto.setSessionId(session.getId());
        sessionDto.setCustomerId(session.getCustomerId());
        sessionDto.setStatus(session.getStatus());
        cartDto.setSession(sessionDto);

        cartDto.setCartItems(cart.getCartItems().stream().map(cartItem -> {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setCartItemId(cartItem.getId());
            cartItemDto.setProduct(Reusable.mapProductToProductInCartDto(cartItem.getProduct()));
            cartItemDto.setQuantity(cartItem.getQuantity());
            return cartItemDto;
        }).toList());

        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@RequestBody DeleteCartItemRequestDto requestDto, @AuthenticationPrincipal Jwt jwt) {
        Long customerId = jwt.getClaim("user_id");
        this.cartService.removeCartItem(customerId, requestDto.getCartItemId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UpdateCartItemResponseDto> updateCartItem(@RequestBody UpdateCartItemRequestDto requestDto, @AuthenticationPrincipal Jwt jwt) {
        Long customerId = jwt.getClaim("user_id");
        CartItem cartItem = this.cartService.updateCartItem(customerId, requestDto.getCartItemId(), requestDto.getIncrementVal());
        return ResponseEntity.ok(UpdateCartItemResponseDto.from(cartItem));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDetailsDto> checkoutCart(@RequestBody CheckoutCartRequestDto requestDto, @AuthenticationPrincipal Jwt jwt) {
        Long customerId = jwt.getClaim("user_id");
        Order order = this.cartService.checkoutCart(customerId, requestDto.getDeliveryAddressId());
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDetailsDto.from(order));
    }
}
