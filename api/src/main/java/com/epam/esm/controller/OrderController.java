package com.epam.esm.controller;

import com.epam.esm.representation.OrderModel;
import com.epam.esm.representation.assembler.OrderModelAssembler;
import com.epam.esm.service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * The class provides order operations
 */
@RestController
@RequestMapping(value = "/orders")
@Validated
public class OrderController {
    private final OrderService orderService;

    private final OrderModelAssembler orderModelAssembler;

    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler) {
        this.orderService = orderService;
        this.orderModelAssembler = orderModelAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel makeOrder(
            @RequestParam @Min(value = 1, message = "{valid_id}") Long userId,
            @RequestParam @Min(value = 1, message = "{valid_id}") Long certificateId) {
        return orderModelAssembler.toModel(orderService.makeOrder(userId, certificateId));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderModel getOrder(
            @PathVariable("id") @Min(value = 1, message = "{valid_id}") Long id) {
        return orderModelAssembler.toModel(orderService.findById(id));
    }
}
