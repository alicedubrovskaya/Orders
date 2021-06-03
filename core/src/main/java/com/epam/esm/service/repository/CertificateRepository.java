package com.epam.esm.service.repository;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.SortParamsDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.enumeration.SortOrder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CertificateRepository {

    Certificate create(Certificate certificate);

    Optional<Certificate> findById(Long id);

    List<Certificate> findByParamsAndSort(CertificateDto certificateDto, Map<String, SortOrder> sortOrders, PageDto pageDto);

    long countAllByParams(CertificateDto certificateDto, Map<String, SortOrder> sortOrders);

    Certificate update(Certificate certificate);

    void delete(Long id);
}
