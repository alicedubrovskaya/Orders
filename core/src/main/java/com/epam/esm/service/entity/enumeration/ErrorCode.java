package com.epam.esm.service.entity.enumeration;

/**
 * 40401 (xxx y z)
 * xxx- HTTP status (404)
 * y- Common - 0, Validation - 1, Exists - 2, Page - 3
 * z- Tag - 1, User - 2, Order - 3, Certificate - 4, Search - 5, Common - 0
 */
public enum ErrorCode {
    NOT_VALID_TAG(40011),
    NOT_VALID_CERTIFICATE(40014),
    NOT_VALID_SEARCH(40015),
    NOT_VALID(40000),

    ALREADY_EXISTS_TAG(40021),
    CERTIFICATE_IS_INCLUDED_IN_ORDERS(40004),

    NO_TAG_WITH_ID(40401),
    NO_USER_WITH_ID(40402),
    NO_ORDER_WITH_ID(40403),
    NO_CERTIFICATE_WITH_ID(40404),

    NO_PAGE_WITH_ORDERS(40433),
    NO_PAGE_WITH_TAGS(40431),
    NO_PAGE_WITH_CERTIFICATES(40434),

    USER_HAS_NO_TAGS(40402),

    COULD_NOT_EXECUTE_STATEMENT(404),
    METHOD_NOT_FOUND(404),
    METHOD_ARGUMENT_TYPE_MISMATCH(404),
    UNSUPPORTED_MEDIA_TYPE(415),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
