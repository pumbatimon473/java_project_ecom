package com.project.ecom.exceptions;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException(Long userId) {
        super(String.format("User with id %d is not authorized to make this request.", userId));
    }
}
