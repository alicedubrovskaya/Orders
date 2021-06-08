package com.epam.esm.representation.assembler;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.representation.OrderModel;
import com.epam.esm.representation.PriceModel;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<OrderDto, OrderModel> {

    private final CertificateModelAssembler certificateModelAssembler;

    @Autowired
    public OrderModelAssembler(CertificateModelAssembler certificateModelAssembler) {
        super(OrderController.class, OrderModel.class);
        this.certificateModelAssembler = certificateModelAssembler;
    }

    @Override
    public OrderModel toModel(OrderDto orderDto) {
        OrderModel orderModel = instantiateModel(orderDto);
        orderModel.add(
                linkTo(methodOn(OrderController.class).getOrder(orderDto.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).makeOrder(null)).withSelfRel()
        );
        orderModel.setId(orderDto.getId());
        orderModel.setPrice(new PriceModel(orderDto.getPrice().getCost(), orderDto.getPrice().getCurrency()));
        orderModel.setPurchaseDate(orderDto.getPurchaseDate());
        orderModel.setCertificate(certificateModelAssembler.toModel(orderDto.getCertificate()));
        return orderModel;
    }


    @Override
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends OrderDto> orderDtos) {
        CollectionModel<OrderModel> collectionModel = super.toCollectionModel(orderDtos);
        collectionModel.add(linkTo(methodOn(OrderController.class).makeOrder(null))
                .withRel("makeOrder"));
        return collectionModel;
    }
}
