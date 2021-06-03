package com.epam.esm.service.service.converter;


import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.entity.Tag;

public class TagDtoConverter implements DtoConverter<Tag, TagDto> {
    @Override
    public TagDto convertToDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    @Override
    public Tag convertToEntity(TagDto tagDto, Tag tag) {
        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName());
        return tag;
    }
}
