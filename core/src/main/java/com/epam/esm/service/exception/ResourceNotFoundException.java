package com.epam.esm.service.exception;

import com.epam.esm.service.entity.enumeration.ErrorCode;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.entity.enumeration.RequestedResource;

public class ResourceNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Long requestedResourceId;

    public ResourceNotFoundException(ErrorCode errorCode, Long requestedResourceId) {
        this.errorCode = errorCode;
        this.requestedResourceId = requestedResourceId;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Long getRequestedResourceId() {
        return requestedResourceId;
    }
}
