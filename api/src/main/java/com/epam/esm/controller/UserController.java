package com.epam.esm.controller;

import com.epam.esm.representation.OrderModel;
import com.epam.esm.representation.UserModel;
import com.epam.esm.representation.assembler.OrderModelAssembler;
import com.epam.esm.representation.assembler.UserModelAssembler;
import com.epam.esm.representation.page.PageModel;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.service.PageService;
import com.epam.esm.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * The class provides tag operations
 */
@RestController
@RequestMapping(value = "/users")
@Validated
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final PageService pageService;
    private final UserModelAssembler userModelAssembler;
    private final OrderModelAssembler orderModelAssembler;

    @Autowired
    public UserController(UserService userService,
                          OrderService orderService,
                          PageService pageService,
                          UserModelAssembler userModelAssembler,
                          OrderModelAssembler orderModelAssembler) {
        this.userService = userService;
        this.orderService = orderService;
        this.pageService = pageService;
        this.userModelAssembler = userModelAssembler;
        this.orderModelAssembler = orderModelAssembler;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserModel getUser(@PathVariable("id") @Min(value = 1, message = "{valid_id}") Long id) {
        return userModelAssembler.toModel(userService.findById(id));
    }


    @GetMapping("/{userId}/orders")
    @ResponseStatus(HttpStatus.OK)
    public PageModel<OrderModel> getOrders(
            @PathVariable @Min(value = 1, message = "{valid_id}") Long userId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {

        PageDto pageDto = new PageDto(page, size);
        List<OrderDto> orders = orderService.findByUser(userId, pageDto);
        return new PageModel<>(orderModelAssembler.toCollectionModel(orders), pageService.buildOrdersPage(pageDto, userId));
    }
}
