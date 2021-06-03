package com.epam.esm.service.exception;

import com.epam.esm.service.entity.enumeration.ErrorCode;
import com.epam.esm.service.entity.enumeration.ErrorMessage;

import java.util.List;

public class ValidationException extends RuntimeException {
    private List<ErrorMessage> errors;
    ErrorCode errorCode;

    public ValidationException() {
    }

    public ValidationException(List<ErrorMessage> errors, ErrorCode errorCode) {
        this.errors = errors;
        this.errorCode = errorCode;
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
