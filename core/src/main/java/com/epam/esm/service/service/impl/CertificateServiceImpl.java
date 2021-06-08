package com.epam.esm.service.service.impl;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Order;
import com.epam.esm.service.entity.enumeration.ErrorCode;
import com.epam.esm.service.entity.enumeration.SortOrder;
import com.epam.esm.service.exception.CertificateDeleteException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.repository.CertificateRepository;
import com.epam.esm.service.repository.OrderRepository;
import com.epam.esm.service.service.CertificateService;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.service.builder.SearchParamsBuilder;
import com.epam.esm.service.service.converter.CertificateDtoConverter;
import com.epam.esm.service.service.converter.CertificatePatchDtoConverter;
import com.epam.esm.service.service.converter.DtoConverter;
import com.epam.esm.service.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
public class CertificateServiceImpl implements CertificateService {
    private final TagService tagService;
    private final CertificateRepository certificateRepository;
    private final OrderRepository orderRepository;
    private final Validator<CertificateDto> certificateDtoValidator;
    private final Validator<SearchCertificateDto> searchCertificateDtoValidator;
    private final Validator<CertificateDto> certificatePatchValidator;

    @Autowired
    public CertificateServiceImpl(TagService tagService,
                                  CertificateRepository certificateRepository,
                                  OrderRepository orderRepository,
                                  @Qualifier("validator") Validator<CertificateDto> certificateDtoValidator,
                                  @Qualifier("validator_patch") Validator<CertificateDto> certificatePatchValidator,
                                  Validator<SearchCertificateDto> searchCertificateDtoValidator
    ) {
        this.tagService = tagService;
        this.certificateRepository = certificateRepository;
        this.orderRepository = orderRepository;
        this.certificateDtoValidator = certificateDtoValidator;
        this.certificatePatchValidator = certificatePatchValidator;
        this.searchCertificateDtoValidator = searchCertificateDtoValidator;
    }

    @Override
    public CertificateDto create(CertificateDto certificateDto) {
        certificateDtoValidator.validate(certificateDto);
        if (!certificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(certificateDtoValidator.getMessages(), ErrorCode.NOT_VALID_CERTIFICATE);
        }

        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        certificateDto.setTags(tagService.attachTagsWithIds(certificateDto.getTags()));
        Certificate createdCertificate = certificateRepository.create(
                certificateConverter.convertToEntity(certificateDto, new Certificate()));
        return certificateConverter.convertToDto(createdCertificate);
    }

    @Transactional
    @Override
    public CertificateDto update(CertificateDto certificateDto) {
        certificatePatchValidator.validate(certificateDto);
        if (!certificatePatchValidator.getMessages().isEmpty()) {
            throw new ValidationException(certificatePatchValidator.getMessages(), ErrorCode.NOT_VALID_CERTIFICATE);
        }

        if (certificateRepository.findById(certificateDto.getId()).isEmpty()) {
            throw new ResourceNotFoundException(ErrorCode.NO_CERTIFICATE_WITH_ID, certificateDto.getId());
        }

        certificateDto.setTags(tagService.attachTagsWithIds(certificateDto.getTags()));

        Certificate existingCertificate = certificateRepository
                .findById(certificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NO_CERTIFICATE_WITH_ID,
                        certificateDto.getId()));

        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        Certificate certificateForUpdate = certificateConverter.convertToEntity(certificateDto, existingCertificate);
        return certificateConverter.convertToDto(certificateRepository.update(certificateForUpdate));
    }

    @Transactional
    @Override
    public CertificateDto patch(CertificateDto certificateDto) {
        certificatePatchValidator.validate(certificateDto);
        if (!certificatePatchValidator.getMessages().isEmpty()) {
            throw new ValidationException(certificatePatchValidator.getMessages(), ErrorCode.NOT_VALID_CERTIFICATE);
        }

        Certificate existingCertificate = certificateRepository
                .findById(certificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NO_CERTIFICATE_WITH_ID,
                        certificateDto.getId()));

        certificateDto.setTags(tagService.attachTagsWithIds(certificateDto.getTags()));
        CertificatePatchDtoConverter converter = new CertificatePatchDtoConverter();
        Certificate certificateForUpdate = converter.convertToEntity(certificateDto, existingCertificate);

        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        return certificateConverter.convertToDto(certificateRepository.update(certificateForUpdate));
    }

    @Transactional
    @Override
    public void delete(Long certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId).orElseThrow(() -> {
            throw new ResourceNotFoundException(ErrorCode.NO_CERTIFICATE_WITH_ID, certificateId);
        });
        List<Order> orders = orderRepository.findByCertificate(certificate);
        if (!orders.isEmpty()) {
            throw new CertificateDeleteException();
        }
        certificateRepository.delete(certificateId);
    }

    @Override
    public CertificateDto findById(Long certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId).orElseThrow(() -> {
            throw new ResourceNotFoundException(ErrorCode.NO_CERTIFICATE_WITH_ID, certificateId);
        });
        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        return certificateConverter.convertToDto(certificate);
    }

    @Override
    public List<CertificateDto> findAllByParams(SearchCertificateDto searchCertificateDto, @Valid PageDto pageDto) {
        searchCertificateDtoValidator.validate(searchCertificateDto);
        if (!searchCertificateDtoValidator.getMessages().isEmpty()) {
            throw new ValidationException(searchCertificateDtoValidator.getMessages(), ErrorCode.NOT_VALID_SEARCH);
        }

        SearchParamsBuilder searchParamsBuilder = new SearchParamsBuilder();
        CertificateDto certificateDto = searchParamsBuilder.buildCertificate(searchCertificateDto);
        Map<String, SortOrder> sortOrders = searchParamsBuilder.buildSortParams(searchCertificateDto);

        List<Certificate> certificates = certificateRepository.findByParamsAndSort(certificateDto, sortOrders, pageDto);

        DtoConverter<Certificate, CertificateDto> certificateConverter = new CertificateDtoConverter();
        return certificates.stream()
                .map(certificateConverter::convertToDto)
                .collect(Collectors.toList());
    }
}
