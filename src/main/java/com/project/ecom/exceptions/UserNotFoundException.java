package com.project.ecom.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("No user exists with id: " + id);
    }
}
