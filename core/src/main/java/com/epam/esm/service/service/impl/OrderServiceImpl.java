package com.epam.esm.service.service.impl;

import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Order;
import com.epam.esm.service.entity.User;
import com.epam.esm.service.entity.enumeration.ErrorCode;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.exception.PageNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.repository.CertificateRepository;
import com.epam.esm.service.repository.OrderRepository;
import com.epam.esm.service.repository.UserRepository;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.service.converter.DtoConverter;
import com.epam.esm.service.service.converter.OrderDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of order service
 */
@Service
@Validated
public class OrderServiceImpl implements OrderService {

    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(CertificateRepository certificateRepository,
                            UserRepository userRepository,
                            OrderRepository orderRepository) {
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public OrderDto makeOrder(Long userId, Long certificateId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.NO_USER_WITH_ID, userId));

        Certificate certificate = certificateRepository.findById(certificateId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.NO_CERTIFICATE_WITH_ID, certificateId));

        Order order = new Order();
        order.setCertificate(certificate);
        order.setPrice(certificate.getPrice());
        order.setUser(user);

        DtoConverter<Order, OrderDto> orderConverter = new OrderDtoConverter();
        return orderConverter.convertToDto(orderRepository.create(order));
    }

    @Override
    public OrderDto findById(Long orderId) {
        DtoConverter<Order, OrderDto> converter = new OrderDtoConverter();
        return converter.convertToDto(orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.NO_ORDER_WITH_ID, orderId)));
    }

    @Override
    public List<OrderDto> findByUser(Long userId, @Valid PageDto pageDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.NO_USER_WITH_ID, userId));

        DtoConverter<Order, OrderDto> converter = new OrderDtoConverter();
        return orderRepository.findByUser(user, pageDto).stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }
}
