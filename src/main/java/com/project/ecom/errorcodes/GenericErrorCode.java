package com.project.ecom.errorcodes;

public enum GenericErrorCode implements ErrorCode {
    USER_ALREADY_EXISTS(409),
    INTERNAL_SERVER_ERROR(500),
    USER_NOT_FOUND(404),
    NOT_ACCEPTABLE(406),
    UNAUTHORIZED_USER(401),
    PRODUCT_NOT_FOUND(404),
    CART_ITEM_NOT_FOUND(404),
    NO_ACTIVE_CART_LINKED(417),
    NO_ACTIVE_CART(404),
    ADDRESS_NOT_FOUND(404),
    ADDRESS_NOT_LINKED(417);

    private final Integer errorCode;

    GenericErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }
}
