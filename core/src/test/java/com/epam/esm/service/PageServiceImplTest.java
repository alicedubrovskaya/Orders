package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.entity.User;
import com.epam.esm.service.exception.PageNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.repository.CertificateRepository;
import com.epam.esm.service.repository.OrderRepository;
import com.epam.esm.service.repository.TagRepository;
import com.epam.esm.service.repository.UserRepository;
import com.epam.esm.service.service.impl.PageServiceImpl;
import com.epam.esm.service.service.model.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class PageServiceImplTest {
    private final User USER = new User();

    @InjectMocks
    private PageServiceImpl service;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CertificateRepository certificateRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldBuildOrdersPage() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(USER));
        Mockito.when(orderRepository.countAllByUser(Mockito.any(User.class))).thenReturn(9L);
        Page expectedPage = new Page(9L, 5);
        Page actualPage = service.buildOrdersPage(new PageDto(1, 2), 1L);
        Assertions.assertEquals(expectedPage, actualPage);
    }


    @Test
    void shouldThrowExceptionUserNotFoundBuildOrdersPage() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.buildOrdersPage(null, 1L));
    }

    @Test
    void shouldThrowExceptionPageNotFoundBuildOrdersPage() {
        Mockito.when(tagRepository.countAll()).thenReturn(10L);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(USER));
        PageDto pageDto = new PageDto(2, 20);
        Assertions.assertThrows(PageNotFoundException.class, () -> service.buildOrdersPage(pageDto,1L));
    }

    @Test
    void shouldBuildTagsPage() {
        Mockito.when(tagRepository.countAll()).thenReturn(10L);
        Page expectedPage = new Page(10L, 10);
        Page actualPage = service.buildTagsPage(new PageDto(1, 1));
        Assertions.assertEquals(expectedPage, actualPage);
    }

    @Test
    void shouldThrowExceptionBuildTagsPage() {
        Mockito.when(tagRepository.countAll()).thenReturn(10L);
        PageDto pageDto = new PageDto(2, 20);
        Assertions.assertThrows(PageNotFoundException.class, () -> service.buildTagsPage(pageDto));
    }

    @Test
    void shouldBuildCertificatesPage() {
        Mockito.when(certificateRepository.countAllByParams(
                Mockito.any(CertificateDto.class), Mockito.anyMap())).thenReturn(4L);
        Page expectedPage = new Page(4L, 2);
        Page actualPage = service.buildCertificatesPage(new PageDto(2, 2), new SearchCertificateDto());
        Assertions.assertEquals(expectedPage, actualPage);
    }

    @Test
    void shouldThrowExceptionBuildTCertificatesPage() {
        Mockito.when(tagRepository.countAll()).thenReturn(10L);
        PageDto pageDto = new PageDto(2, 20);
        SearchCertificateDto searchCertificateDto = new SearchCertificateDto();
        Assertions.assertThrows(PageNotFoundException.class, () -> service.buildCertificatesPage(pageDto, searchCertificateDto));
    }
}
