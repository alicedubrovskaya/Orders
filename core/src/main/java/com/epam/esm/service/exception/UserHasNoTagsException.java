package com.epam.esm.service.exception;

import com.epam.esm.service.entity.enumeration.ErrorMessage;

public class UserHasNoTagsException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public UserHasNoTagsException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
