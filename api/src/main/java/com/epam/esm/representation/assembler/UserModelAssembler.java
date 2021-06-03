package com.epam.esm.representation.assembler;

import com.epam.esm.controller.UserController;
import com.epam.esm.representation.UserModel;
import com.epam.esm.service.dto.UserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto userDto) {
        UserModel userModel = instantiateModel(userDto);
        UserController controller = methodOn(UserController.class);
        userModel.add(linkTo(controller.getUser(userDto.getId())).withSelfRel());
        userModel.add(linkTo(methodOn(UserController.class).getOrders(null, null, null)).withRel("orders"));

        userModel.setId(userDto.getId());
        userModel.setName(userDto.getName());
        userModel.setPassword(userDto.getPassword());
        return userModel;
    }


    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends UserDto> userDtos) {
        CollectionModel<UserModel> collectionModel = super.toCollectionModel(userDtos);
        return collectionModel;
    }
}
