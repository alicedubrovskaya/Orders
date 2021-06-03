package com.epam.esm.service.exception;

import com.epam.esm.service.entity.enumeration.ErrorCode;
import com.epam.esm.service.entity.enumeration.ErrorMessage;

public class ResourceAlreadyExistsException extends RuntimeException {
    private final ErrorMessage errorMessage;
    private final ErrorCode errorCode;

    public ResourceAlreadyExistsException(ErrorMessage errorMessage, ErrorCode errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
