package com.epam.esm.service.service.builder;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.dto.SortParamsDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.entity.enumeration.SortOrder;
import com.epam.esm.service.service.parser.SearchParamsParser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchParamsBuilder {
    private final SearchParamsParser parser;

    public SearchParamsBuilder() {
        this.parser = new SearchParamsParser();
    }

    public CertificateDto buildCertificate(SearchCertificateDto searchCertificateDto) {
        List<String> names = parser.parseTagNames(searchCertificateDto.getTagNames());
        List<TagDto> tags = names.stream().map(name -> new TagDto(null, name)).collect(Collectors.toList());

        return CertificateDto.builder()
                .name(searchCertificateDto.getCertificateName())
                .description(searchCertificateDto.getDescription())
                .tags(tags)
                .build();
    }

    public Map<String, SortOrder> buildSortParams(SearchCertificateDto searchCertificateDto) {
        return parser.parseSortParams(searchCertificateDto.getSortParams());
    }
}
