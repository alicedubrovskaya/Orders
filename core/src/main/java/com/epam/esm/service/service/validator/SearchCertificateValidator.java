package com.epam.esm.service.service.validator;

import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.service.parser.SearchParamsParser;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SearchCertificateValidator implements Validator<SearchCertificateDto> {
    private final SearchParamsParser parser = new SearchParamsParser();
    private final List<ErrorMessage> errors = new ArrayList<>();
    private static final String CORRECT_TAGS_ENUMERATION = "^([a-z0-9\\_]+[,])*[a-z0-9\\_]+$";
    private static final String CORRECT_SORT_ORDERS_ENUMERATION = "^((asc|desc)\\([a-zA-Z\\_]+\\),)*(asc\\(|desc\\()[a-zA-Z\\_]+\\)$";
    private static final List<String> CORRECT_FIELDS_TO_SORT_BY = Arrays.asList("id", "name", "description",
            "cost", "currency", "duration", "dateOfCreation", "dateOfModification");

    @Override
    public void validate(SearchCertificateDto searchCertificateDto) {
        validateTags(searchCertificateDto.getTagNames());
        validateSortOrders(searchCertificateDto.getSortParams());
    }

    protected void validateTags(String tags) {
        if (tags != null && (!tags.matches(CORRECT_TAGS_ENUMERATION))) {
            errors.add(ErrorMessage.SEARCH_CERTIFICATE_TAGS_INCORRECT);
        }
    }

    protected void validateSortOrders(String sortOrders) {
        if (sortOrders != null && (!sortOrders.matches(CORRECT_SORT_ORDERS_ENUMERATION) ||
                parser.parseSortParams(sortOrders).keySet().stream().
                        anyMatch(order -> !CORRECT_FIELDS_TO_SORT_BY.contains(order)))) {
            errors.add(ErrorMessage.SEARCH_CERTIFICATE_SORT_ORDERS_INCORRECT);
        }
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return errors;
    }
}
