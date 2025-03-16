package com.project.ecom.controllers;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.dtos.*;
import com.project.ecom.models.Cart;
import com.project.ecom.models.CartItem;
import com.project.ecom.models.CustomerSession;
import com.project.ecom.models.Product;
import com.project.ecom.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final ICartService cartService;

    @Autowired
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<AddProductToCartResDto> addProductToCart(@RequestBody AddProductToCartReqDto requestDto) {
        CartItem cartItem = this.cartService.addProductToCart(requestDto.getCustomerId(), requestDto.getProductId(), requestDto.getQuantity());
        AddProductToCartResDto responseDto = new AddProductToCartResDto();
        responseDto.setCartItemId(cartItem.getId());
        responseDto.setCartId(cartItem.getCart().getId());

        Product product = cartItem.getProduct();
        ProductInCartDto productDto = Reusable.mapProductToProductInCartDto(product);
        responseDto.setProduct(productDto);
        responseDto.setQuantity(cartItem.getQuantity());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CartDto> getCart(@PathVariable(name = "customerId") Long customerId) {
        Cart cart = this.cartService.getCart(customerId);
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());

        CustomerSession session = cart.getSession();
        CustomerSessionDto sessionDto = new CustomerSessionDto();
        sessionDto.setSessionId(session.getId());
        sessionDto.setCustomerId(session.getCustomer().getId());
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
    public ResponseEntity<Void> deleteCartItem(@RequestBody DeleteCartItemRequestDto requestDto) {
        this.cartService.removeCartItem(requestDto.getCustomerId(), requestDto.getCartItemId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UpdateCartItemResponseDto> updateCartItem(@RequestBody UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = this.cartService.updateCartItem(requestDto.getCustomerId(), requestDto.getCartItemId(), requestDto.getIncrementVal());
        UpdateCartItemResponseDto responseDto = new UpdateCartItemResponseDto();
        responseDto.setCartItemId(cartItem.getId());
        responseDto.setProductInCartDto(Reusable.mapProductToProductInCartDto(cartItem.getProduct()));
        responseDto.setQuantity(cartItem.getQuantity());
        Cart cart = cartItem.getCart();
        CartStatusDto cartStatusDto = new CartStatusDto(cart.getId(), cart.getStatus());
        responseDto.setCart(cartStatusDto);
        return ResponseEntity.ok(responseDto);
    }
}
