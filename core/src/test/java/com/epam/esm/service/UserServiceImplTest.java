package com.epam.esm.service;

import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.entity.User;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.repository.UserRepository;
import com.epam.esm.service.service.converter.DtoConverter;
import com.epam.esm.service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class UserServiceImplTest {
    private static final User FIRST_USER = new User(1L, "user", "pass", null);
    private static final UserDto FIRST_USER_DTO = new UserDto(1L, "user", "pass");

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private DtoConverter<User, UserDto> converter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(FIRST_USER));
        Mockito.when(converter.convertToDto(Mockito.any(User.class))).thenReturn(FIRST_USER_DTO);
        UserDto userDto = service.findById(1L);
        Assertions.assertEquals(FIRST_USER_DTO, userDto);
    }

    @Test
    void shouldNotGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }
}
