package com.epam.esm.service.service;

import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.service.model.Page;

public interface PageService {

    Page buildOrdersPage(PageDto pageDto, Long userId);

    Page buildTagsPage(PageDto pageDto);

    Page buildCertificatesPage(PageDto pageDto, SearchCertificateDto searchCertificateDto);
}
