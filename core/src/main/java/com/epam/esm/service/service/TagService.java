package com.epam.esm.service.service;

import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.TagDto;

import javax.validation.Valid;
import java.util.List;

public interface TagService {
    TagDto create(TagDto tagDto);

    void delete(Long tagId);

    TagDto findById(Long tagId);

    TagDto findMostPopularOfRichestUser();

    List<TagDto> findAll(@Valid PageDto pageDto);

    List<TagDto> attachTagsWithIds(List<TagDto> tags);
}
