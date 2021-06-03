package com.epam.esm.service.repository.builder;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PredicateBuilder {
    public Optional<Predicate> getNamePredicate(String name, CriteriaBuilder builder, Root<Certificate> root) {
        if (name != null) {
            return Optional.of(builder.like(root.get("name"), '%' + name + '%'));
        }
        return Optional.empty();
    }

    public Optional<Predicate> getDescriptionPredicate(String description,
                                                       CriteriaBuilder builder,
                                                       Root<Certificate> root) {
        if (description != null) {
            return Optional.of(builder.like(root.get("description"), '%' + description + '%'));
        }
        return Optional.empty();
    }

    public Optional<Predicate> getTagPredicate(List<TagDto> tags, Root<Certificate> root) {
        if (!tags.isEmpty()) {
            List<String> tagNames = tags.stream().map(TagDto::getName).collect(Collectors.toList());
            Join<Certificate, Tag> join = root.join("tags");
            return Optional.of(join.get("name").in(tagNames));
        }
        return Optional.empty();
    }
}
