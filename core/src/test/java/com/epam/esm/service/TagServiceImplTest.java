package com.epam.esm.service;

import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.entity.Tag;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.UserHasNoTagsException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.repository.TagRepository;
import com.epam.esm.service.service.converter.DtoConverter;
import com.epam.esm.service.service.impl.TagServiceImpl;
import com.epam.esm.service.service.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class TagServiceImplTest {
    private static final Tag FIRST_TAG = new Tag(1L, "Tag1", null);
    private static final TagDto FIRST_TAG_DTO = new TagDto(1L, "Tag1");

    private static final Tag SECOND_TAG = new Tag(2L, "Tag2", null);
    private static final TagDto SECOND_TAG_DTO = new TagDto(2L, "Tag2");

    private static final List<ErrorMessage> errorMessages = Collections.singletonList(ErrorMessage.TAG_NAME_INCORRECT);


    @InjectMocks
    private TagServiceImpl service;

    @Mock
    private TagRepository repository;

    @Mock
    private DtoConverter<Tag, TagDto> converter;

    @Mock
    private Validator<TagDto> validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(FIRST_TAG));
        Mockito.when(converter.convertToDto(Mockito.any(Tag.class))).thenReturn(FIRST_TAG_DTO);
        TagDto tagDto = service.findById(1L);
        Assertions.assertEquals(FIRST_TAG_DTO, tagDto);
    }

    @Test
    void shouldNotGetById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void shouldDeleteTagById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(FIRST_TAG));
        service.delete(1L);
        Mockito.verify(repository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void shouldNotDeleteTagById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void shouldFindAll() {
        PageDto pageDto = new PageDto(1, 1);
        service.findAll(pageDto);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageDto);
    }

    @Test
    void shouldCreateTag() throws ValidationException {
        Mockito.when(converter.convertToEntity(Mockito.any(TagDto.class), Mockito.any(Tag.class))).thenReturn(FIRST_TAG);
        Mockito.when(repository.create(Mockito.any(Tag.class))).thenReturn(FIRST_TAG);
        Mockito.when(converter.convertToDto(Mockito.any(Tag.class))).thenReturn(FIRST_TAG_DTO);

        TagDto tagDto = service.create(FIRST_TAG_DTO);
        Assertions.assertEquals(FIRST_TAG_DTO, tagDto);
    }

    @Test
    void shouldNotCreateTag() {
        Mockito.when(validator.getMessages()).thenReturn(errorMessages);
        Assertions.assertThrows(ValidationException.class, () -> service.create(SECOND_TAG_DTO));
    }

    @Test
    void shouldFindMostPopularOfRichestUser() {
        Mockito.when(repository.findMostPopular()).thenReturn(Optional.of(FIRST_TAG));
        Mockito.when(converter.convertToDto(Mockito.any(Tag.class))).thenReturn(FIRST_TAG_DTO);
        TagDto tagDto = service.findMostPopularOfRichestUser();
        Assertions.assertEquals(FIRST_TAG_DTO, tagDto);
    }

    @Test
    void shouldNotFindMostPopularOfRichestUser() {
        Mockito.when(repository.findMostPopular()).thenReturn(Optional.empty());
        Assertions.assertThrows(UserHasNoTagsException.class, () -> service.findMostPopularOfRichestUser());
    }

    @Test
    void attachTagsWithIds() {
        Mockito.when(repository.findByNames(Mockito.anyList())).thenReturn(Arrays.asList(
                FIRST_TAG,
                SECOND_TAG));

        List<TagDto> tags = Arrays.asList(
                new TagDto(null, "Tag1"),
                new TagDto(null, "Tag2"),
                new TagDto(null, "Tag3"));

        List<TagDto> expectedResult = Arrays.asList(
                FIRST_TAG_DTO,
                SECOND_TAG_DTO,
                new TagDto(null, "Tag3"));

        Mockito.when(converter.convertToDto(FIRST_TAG)).thenReturn(FIRST_TAG_DTO);
        Mockito.when(converter.convertToDto(SECOND_TAG)).thenReturn(SECOND_TAG_DTO);
        List<TagDto> actualResult = service.attachTagsWithIds(tags);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
