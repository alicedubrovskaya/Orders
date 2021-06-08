package com.epam.esm.service;

import com.epam.esm.service.dto.*;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Order;
import com.epam.esm.service.entity.Price;
import com.epam.esm.service.entity.User;
import com.epam.esm.service.entity.enumeration.Currency;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.repository.CertificateRepository;
import com.epam.esm.service.repository.OrderRepository;
import com.epam.esm.service.repository.UserRepository;
import com.epam.esm.service.service.converter.DtoConverter;
import com.epam.esm.service.service.impl.OrderServiceImpl;
import com.epam.esm.service.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class OrderServiceImplTest {
    private static final Certificate CERTIFICATE = new Certificate();
    private static final CertificateDto CERTIFICATE_DTO = new CertificateDto();
    private static final PriceDto PRICE_DTO = new PriceDto(new BigDecimal(3), Currency.BYN);
    private static final User USER = new User();

    private static final Order ORDER = Order.builder()
            .id(1L)
            .purchaseDate(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .price(new Price(new BigDecimal(3), Currency.BYN))
            .certificate(CERTIFICATE)
            .user(USER)
            .build();

    private static final OrderDto ORDER_DTO = OrderDto.builder()
            .id(1L)
            .purchaseDate(LocalDateTime.of(2021, 5, 1, 18, 35, 37))
            .price(PRICE_DTO)
            .certificate(CERTIFICATE_DTO)
            .build();
    @InjectMocks
    private OrderServiceImpl service;

    @Mock
    private OrderRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private DtoConverter<Order, OrderDto> converter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(ORDER));
        Mockito.when(converter.convertToDto(Mockito.any(Order.class))).thenReturn(ORDER_DTO);
        OrderDto orderDto = service.findById(1L);
        Assertions.assertEquals(ORDER_DTO, orderDto);
    }

    @Test
    void shouldNotGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void shouldMakeOrder() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(USER));
        Mockito.when(certificateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(CERTIFICATE));
        Mockito.when(repository.create(Mockito.any(Order.class))).thenReturn(ORDER);
        Mockito.when(converter.convertToDto(Mockito.any(Order.class))).thenReturn(ORDER_DTO);
        OrderDto orderDto = service.makeOrder(new OrderParamsDto(1L, 1L));
        Assertions.assertEquals(ORDER_DTO, orderDto);
    }

    @Test
    void shouldThrowExceptionUserNotFoundMakeOrder() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void shouldThrowExceptionCertificateNotFoundMakeOrder() {
        Mockito.when(certificateRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void shouldFindByUser() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(USER));
        Mockito.when(repository.findByUser(Mockito.any(User.class), Mockito.any(PageDto.class))).thenReturn(
                Collections.singletonList(ORDER));
        Mockito.when(converter.convertToDto(Mockito.any(Order.class))).thenReturn(ORDER_DTO);

        List<OrderDto> expectedOrders = Collections.singletonList(ORDER_DTO);
        List<OrderDto> orders = service.findByUser(1L, new PageDto(1, 1));
        Assertions.assertEquals(expectedOrders, orders);
    }

    @Test
    void shouldThrowExceptionFindByUser() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }
}
