package com.project.ecom.controllers;

import com.project.ecom.dtos.ErrorDto;
import com.project.ecom.errorcodes.GenericErrorCode;
import com.project.ecom.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorDto(GenericErrorCode.USER_ALREADY_EXISTS, e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(GenericErrorCode.USER_NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(GenericErrorCode.PRODUCT_NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(NoActiveUserSessionException.class)
    public ResponseEntity<ErrorDto> handleNoActiveUserSessionException(NoActiveUserSessionException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(GenericErrorCode.NO_ACTIVE_USER_SESSION, e.getMessage()));
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartItemNotFoundException(CartItemNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(GenericErrorCode.CART_ITEM_NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(NoActiveCartLinkedException.class)
    public ResponseEntity<ErrorDto> handleNoActiveCartLinkedException(NoActiveCartLinkedException e) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ErrorDto(GenericErrorCode.NO_ACTIVE_CART_LINKED, e.getMessage()));
    }

    @ExceptionHandler(NoActiveCartException.class)
    public ResponseEntity<ErrorDto> handleNoActiveCartException(NoActiveCartException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(GenericErrorCode.NO_ACTIVE_CART, e.getMessage()));
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorDto> handleAddressNotFoundException(AddressNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(GenericErrorCode.ADDRESS_NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(AddressNotLinkedException.class)
    public ResponseEntity<ErrorDto> handleAddressNotLinkedException(AddressNotLinkedException e) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ErrorDto(GenericErrorCode.ADDRESS_NOT_LINKED, e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorDto> handleUnauthorizedUserException(UnauthorizedUserException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto(GenericErrorCode.UNAUTHORIZED_USER, e.getMessage()));
    }

    @ExceptionHandler(PaymentLinkCreationException.class)
    public ResponseEntity<ErrorDto> handlePaymentLinkCreationException(PaymentLinkCreationException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(GenericErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(PaymentTransactionRetrievalException.class)
    public ResponseEntity<ErrorDto> handlePaymentTransactionRetrievalException(PaymentTransactionRetrievalException e) {
        return ResponseEntity
                .status(HttpStatus.FAILED_DEPENDENCY)
                .body(new ErrorDto(GenericErrorCode.PAYMENT_GATEWAY_ERROR, e.getMessage()));
    }

    @ExceptionHandler(OrderConfirmationException.class)
    public ResponseEntity<ErrorDto> handleOrderConfirmationException(OrderConfirmationException e) {
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .body(new ErrorDto(GenericErrorCode.INSUFFICIENT_PRODUCT_INVENTORY, e.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(GenericErrorCode.ORDER_NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneralException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(GenericErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorDto(GenericErrorCode.NOT_ACCEPTABLE, e.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDto> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorDto(GenericErrorCode.NOT_ACCEPTABLE, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<String> handleExternalServiceException(ExternalServiceException e) {
        System.out.println(":: DEBUG LOG :: ExternalServiceException Handler Called ...");
        return ResponseEntity
                .status(e.getStatusCode())
                .body(e.getErrorBody());
    }
}
