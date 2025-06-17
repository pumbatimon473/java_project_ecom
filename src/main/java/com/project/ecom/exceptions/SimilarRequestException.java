package com.project.ecom.exceptions;

public class SimilarRequestException extends RuntimeException {
    public SimilarRequestException(String msg) {
        super(msg);
    }
}
