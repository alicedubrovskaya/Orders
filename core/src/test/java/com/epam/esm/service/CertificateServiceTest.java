package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PriceDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Order;
import com.epam.esm.service.entity.User;
import com.epam.esm.service.entity.enumeration.Currency;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.exception.CertificateDeleteException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.repository.CertificateRepository;
import com.epam.esm.service.repository.OrderRepository;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.service.converter.DtoConverter;
import com.epam.esm.service.service.impl.CertificateServiceImpl;
import com.epam.esm.service.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class CertificateServiceTest {
    private static final PriceDto PRICE_DTO = new PriceDto(new BigDecimal(3), Currency.BYN);
    private static final User USER = new User();

    private static final Certificate CERTIFICATE = Certificate.builder()
            .id(1L)
            .name("Certificate1")
            .description("First Description")
            .duration(Duration.ofDays(8))
            .dateOfCreation(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .dateOfModification(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .tags(null)
            .build();

    private static final CertificateDto CERTIFICATE_DTO = CertificateDto.builder()
            .id(1L)
            .name("Certificate1")
            .description("First Description")
            .duration(Duration.ofDays(8))
            .dateOfCreation(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .dateOfModification(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .tags(new ArrayList<>())
            .build();

    private static final List<ErrorMessage> errorMessages = Collections.singletonList(ErrorMessage.CERTIFICATE_NAME_INCORRECT);

    @InjectMocks
    private CertificateServiceImpl service;

    @Mock
    private CertificateRepository repository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TagService tagService;

    @Mock
    DtoConverter<Certificate, CertificateDto> certificateConverter;

    @Mock
    private Validator<TagDto> validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldDeleteById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(CERTIFICATE));
        Mockito.when(orderRepository.findByCertificate(Mockito.any(Certificate.class))).thenReturn(new ArrayList<>());
        service.delete(1L);
        Mockito.verify(repository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void shouldNotDeleteById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(6L));
    }

    @Test
    void shouldNotDeleteByIdThrowCertificateDeleteException() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(CERTIFICATE));
        List<Order> orders = new ArrayList<>(Collections.singletonList(new Order()));
        Mockito.when(orderRepository.findByCertificate(Mockito.any(Certificate.class))).thenReturn(orders);
        Assertions.assertThrows(CertificateDeleteException.class, () -> service.delete(1L));
    }

    @Test
    void shouldCreateCertificate() throws ValidationException {
        Mockito.when(certificateConverter.convertToEntity(Mockito.any(CertificateDto.class), Mockito.any(Certificate.class))).thenReturn(CERTIFICATE);
        Mockito.when(tagService.attachTagsWithIds(Mockito.any(List.class))).thenReturn(new ArrayList());
        Mockito.when(repository.create(Mockito.any(Certificate.class))).thenReturn(CERTIFICATE);
        Mockito.when(certificateConverter.convertToDto(Mockito.any(Certificate.class))).thenReturn(CERTIFICATE_DTO);
        CertificateDto certificateDto = service.create(CERTIFICATE_DTO);
        Assertions.assertEquals(CERTIFICATE_DTO, certificateDto);
    }

    @Test
    void shouldNotCreate() throws ValidationException {
        Mockito.when(validator.getMessages()).thenReturn(errorMessages);
        Assertions.assertThrows(ValidationException.class, () -> service.create(CERTIFICATE_DTO));
    }

    @Test
    void shouldUpdate() {
        Mockito.when(certificateConverter.convertToEntity(Mockito.any(CertificateDto.class), Mockito.any(Certificate.class))).thenReturn(CERTIFICATE);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(CERTIFICATE));
        Mockito.when(tagService.attachTagsWithIds(Mockito.any(List.class))).thenReturn(new ArrayList());
        Mockito.when(repository.update(Mockito.any(Certificate.class))).thenReturn(CERTIFICATE);
        Mockito.when(certificateConverter.convertToDto(Mockito.any(Certificate.class))).thenReturn(CERTIFICATE_DTO);
        CertificateDto certificateDto = service.update(CERTIFICATE_DTO);
        Assertions.assertEquals(CERTIFICATE_DTO, certificateDto);
    }

    @Test
    void shouldNotGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

}
