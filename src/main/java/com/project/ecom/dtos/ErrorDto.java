package com.project.ecom.dtos;

import com.project.ecom.errorcodes.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDto {
    private ErrorCode errorCode;
    private String errorMessage;

    public ErrorDto(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
