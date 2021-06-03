package com.epam.esm.service.service.converter;


import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.entity.User;

public class UserDtoConverter implements DtoConverter<User, UserDto> {
    @Override
    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

    @Override
    public User convertToEntity(UserDto userDto, User user) {
        user.setId(userDto.getId());
        user.setName(user.getName());
        user.setPassword(user.getPassword());
        return user;
    }
}
