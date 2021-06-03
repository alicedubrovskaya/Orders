package com.epam.esm.service.service.impl;

import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.entity.User;
import com.epam.esm.service.entity.enumeration.ErrorCode;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.entity.enumeration.RequestedResource;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.repository.UserRepository;
import com.epam.esm.service.service.UserService;
import com.epam.esm.service.service.converter.DtoConverter;
import com.epam.esm.service.service.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of user service
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findById(Long userId) {
        DtoConverter<User, UserDto> userConverter = new UserDtoConverter();
        return userConverter.convertToDto(userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.NO_USER_WITH_ID, userId)));
    }
}
