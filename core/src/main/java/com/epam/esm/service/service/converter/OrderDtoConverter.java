package com.epam.esm.service.service.converter;


import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.PriceDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Order;

public class OrderDtoConverter implements DtoConverter<Order, OrderDto> {
    private final DtoConverter<Certificate, CertificateDto> certificateConverter;

    public OrderDtoConverter() {
        this.certificateConverter = new CertificateDtoConverter();
    }

    @Override
    public OrderDto convertToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .price(new PriceDto(order.getPrice().getCost(), order.getPrice().getCurrency()))
                .purchaseDate(order.getPurchaseDate())
                .certificate(certificateConverter.convertToDto(order.getCertificate()))
                .build();
    }

    @Override
    public Order convertToEntity(OrderDto orderDto, Order order) {
        return null;
    }
}
