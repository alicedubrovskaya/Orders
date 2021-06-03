package com.epam.esm.service.entity.enumeration;

public enum RequestedResource {
    CERTIFICATE("01"), TAG("02"), USER("03"), ORDER("04");

    private final String errorCode;

    RequestedResource(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
