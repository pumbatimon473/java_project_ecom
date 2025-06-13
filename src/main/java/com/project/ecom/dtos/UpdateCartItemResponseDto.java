package com.project.ecom.dtos;

import com.project.ecom.controllers.reusables.Reusable;
import com.project.ecom.models.Cart;
import com.project.ecom.models.CartItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemResponseDto {
    private Long cartItemId;
    private CartStatusDto cart;
    private ProductInCartDto productInCartDto;
    private Integer quantity;

    public static UpdateCartItemResponseDto from(CartItem cartItem) {
        UpdateCartItemResponseDto responseDto = new UpdateCartItemResponseDto();
        responseDto.setCartItemId(cartItem.getId());
        responseDto.setProductInCartDto(Reusable.mapProductToProductInCartDto(cartItem.getProduct()));
        responseDto.setQuantity(cartItem.getQuantity());
        Cart cart = cartItem.getCart();
        CartStatusDto cartStatusDto = new CartStatusDto(cart.getId(), cart.getStatus());
        responseDto.setCart(cartStatusDto);
        return responseDto;
    }
}
