package com.project.ecom.exceptions;

public class NoActiveCartLinkedException extends RuntimeException {
    public NoActiveCartLinkedException(Long cartItemId) {
        super(String.format("No active cart linked with the cart item id %d", cartItemId));
    }
}
