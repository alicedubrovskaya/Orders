package com.epam.esm.service.service.impl;

import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.entity.Tag;
import com.epam.esm.service.entity.enumeration.ErrorCode;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.exception.ResourceAlreadyExistsException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.UserHasNoTagsException;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.repository.TagRepository;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.service.converter.DtoConverter;
import com.epam.esm.service.service.converter.TagDtoConverter;
import com.epam.esm.service.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final Validator<TagDto> tagValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, Validator<TagDto> tagValidator) {
        this.tagRepository = tagRepository;
        this.tagValidator = tagValidator;
    }

    @Transactional
    @Override
    public TagDto create(TagDto tagDto) {
        tagValidator.validate(tagDto);
        if (!tagValidator.getMessages().isEmpty()) {
            throw new ValidationException(tagValidator.getMessages(), ErrorCode.NOT_VALID_TAG);
        }

        if (tagRepository.findByName(tagDto.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessage.RESOURCE_ALREADY_EXISTS, ErrorCode.ALREADY_EXISTS_TAG);
        }

        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        Tag createdTag = tagRepository.create(tagConverter.convertToEntity(tagDto, new Tag()));
        return tagConverter.convertToDto(createdTag);
    }

    @Transactional
    @Override
    public void delete(Long tagId) {
        tagRepository.findById(tagId).ifPresentOrElse(t -> tagRepository.delete(tagId), () -> {
            throw new ResourceNotFoundException(ErrorCode.NO_TAG_WITH_ID, tagId);
        });
    }

    @Override
    public TagDto findById(Long tagId) {
        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        return tagConverter.convertToDto(tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorCode.NO_TAG_WITH_ID, tagId)));
    }

    @Override
    public TagDto findMostPopularOfRichestUser() {
        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        return tagConverter.convertToDto(tagRepository.findMostPopular().orElseThrow(() ->
                new UserHasNoTagsException(ErrorMessage.USER_HAS_NO_TAGS)));
    }

    @Override
    public List<TagDto> findAll(@Valid PageDto pageDto) {
        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        return tagRepository.findAll(pageDto).stream()
                .map(tagConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagDto> attachTagsWithIds(List<TagDto> tags) {
        DtoConverter<Tag, TagDto> tagConverter = new TagDtoConverter();
        List<TagDto> tagsWithId = tagRepository.findByNames(tags.stream().
                map(TagDto::getName).
                collect(Collectors.toList())
        ).stream().map(tagConverter::convertToDto).collect(Collectors.toList());

        tagsWithId.forEach(tagWithId -> tags.stream().filter(tagDto -> tagDto.getName().equals(tagWithId.getName())
        ).forEach(tagDto -> tagDto.setId(tagWithId.getId())));
        return tags;
    }
}
