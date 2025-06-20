package com.project.ecom.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ExternalServiceException extends RuntimeException {
    private final HttpStatusCode statusCode;
    private final String errorBody;

    public ExternalServiceException(String customMessage, HttpStatusCode statusCode, String errorBody, Throwable cause) {
        super(customMessage, cause);
        this.statusCode = statusCode;
        this.errorBody = errorBody;
    }
}
