package com.epam.esm.service.entity.enumeration;

public enum ErrorMessage {
    RESOURCE_NOT_FOUND("resource_not_found"),
    RESOURCE_ALREADY_EXISTS("resource_already_exists"),
    PAGE_NOT_FOUND("page_not_found"),
    USER_HAS_NO_TAGS("user_has_no_tags"),
    SOMETHING_GOES_WRONG("something_goes_wrong"),
    UNSUPPORTED_MEDIA_TYPE("unsupported_media_type"),
    INCORRECT_REQUEST_VALUE("incorrect_request_value"),
    METHOD_ARGUMENT_TYPE_MISMATCH("method_argument_type_mismatch"),
    METHOD_NOT_FOUND("method_not_found"),
    COULD_NOT_EXECUTE_STATEMENT("could_not_execute_statement"),
    CERTIFICATE_IS_INCLUDED_IN_ORDERS("certificate_is_included_in_orders"),

    TAG_DTO_EMPTY("empty_tag"),
    TAG_NAME_EMPTY("empty_tag_name"),
    TAG_NAME_INCORRECT("incorrect_tag_name"),
    CERTIFICATE_DTO_EMPTY("empty_certificate"),
    CERTIFICATE_NAME_EMPTY("empty_certificate_name"),
    CERTIFICATE_NAME_INCORRECT("incorrect_certificate_name"),
    CERTIFICATE_DESCRIPTION_INCORRECT("incorrect_certificate_description"),
    CERTIFICATE_PRICE_EMPTY("empty_certificate_price"),
    CERTIFICATE_PRICE_COST_INCORRECT("incorrect_certificate_price_cost"),
    CERTIFICATE_DURATION_INCORRECT("incorrect_certificate_duration"),
    SEARCH_CERTIFICATE_TAGS_INCORRECT("incorrect_search_tags"),
    SEARCH_CERTIFICATE_SORT_ORDERS_INCORRECT("incorrect_sort_orders"),
    CERTIFICATE_PRICE_FIELD_EMPTY("empty_certificate_price_field");

    private final String type;

    ErrorMessage(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
