package com.epam.esm.service.repository;

import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Tag create(Tag tag);

    Optional<Tag> findById(Long id);

    Optional<Tag> findByName(String name);

    List<Tag> findByNames(List<String> tagNames);

    Optional<Tag> findMostPopular();

    List<Tag> findAll(PageDto pageDto);

    long countAll();

    void delete(Long id);
}
