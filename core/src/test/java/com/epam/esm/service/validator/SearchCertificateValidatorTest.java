package com.epam.esm.service.validator;

import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.validator.extension.SearchCertificateValidatorForTesting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class SearchCertificateValidatorTest {

    @InjectMocks
    private SearchCertificateValidatorForTesting validator;

    private static final SearchCertificateDto CORRECT_SEARCH_CERTIFICATE_DTO = SearchCertificateDto.builder()
            .tagNames("tag1,tag2")
            .sortParams("asc(cost),desc(id)")
            .build();
    private static final String SORT_ORDERS = "asc(cost),desc(duration)";
    private static final String SORT_ORDERS_INCORRECT = "asc(cost";
    private static final String SORT_ORDERS_INCORRECT_SEPARATION = "asc(cost),,desc(name)";
    private static final String SORT_ORDERS_WITH_NOT_EXISTING_FIELD = "asc(test)";

    private static final String TAGS = "tag1,tag2";
    private static final String TAGS_INCORRECT = "tag1,&tag2";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateTest() {
        validator.validate(CORRECT_SEARCH_CERTIFICATE_DTO);
        Assertions.assertEquals(0, validator.getMessages().size());
    }

    @Test
    void validateTagsTest() {
        validator.validateTags(TAGS);
        Assertions.assertEquals(0, validator.getMessages().size());
    }

    @Test
    void validateIncorrectTagsTest() {
        validator.validateTags(TAGS_INCORRECT);
        Assertions.assertTrue(
                validator.getMessages().contains(ErrorMessage.SEARCH_CERTIFICATE_TAGS_INCORRECT));
    }

    @Test
    void validateFieldsToSortByTest() {
        validator.validateSortOrders(SORT_ORDERS);
        Assertions.assertEquals(0, validator.getMessages().size());
    }

    @Test
    void validateIncorrectFieldsToSortByTest() {
        validator.validateSortOrders(SORT_ORDERS_INCORRECT);
        Assertions.assertTrue(
                validator.getMessages().contains(ErrorMessage.SEARCH_CERTIFICATE_SORT_ORDERS_INCORRECT));
    }

    @Test
    void validateIncorrectSeparationFieldsToSortByTest() {
        validator.validateSortOrders(SORT_ORDERS_INCORRECT_SEPARATION);
        Assertions.assertTrue(
                validator.getMessages().contains(ErrorMessage.SEARCH_CERTIFICATE_SORT_ORDERS_INCORRECT));
    }

    @Test
    void validateSortOrdersWithNotExistingField() {
        validator.validateSortOrders(SORT_ORDERS_WITH_NOT_EXISTING_FIELD);
        Assertions.assertTrue(
                validator.getMessages().contains(ErrorMessage.SEARCH_CERTIFICATE_SORT_ORDERS_INCORRECT));
    }

}