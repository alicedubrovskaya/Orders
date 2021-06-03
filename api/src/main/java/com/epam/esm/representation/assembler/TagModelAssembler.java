package com.epam.esm.representation.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.representation.TagModel;
import com.epam.esm.service.dto.TagDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler extends RepresentationModelAssemblerSupport<TagDto, TagModel> {

    public TagModelAssembler() {
        super(TagController.class, TagModel.class);
    }

    @Override
    public TagModel toModel(TagDto tagDto) {
        TagModel tagModel = instantiateModel(tagDto);
        TagController controller = methodOn(TagController.class);
        tagModel.add(
                linkTo(controller.deleteTag(tagDto.getId())).withRel("delete")
        );
        tagModel.setId(tagDto.getId());
        tagModel.setName(tagDto.getName());
        return tagModel;
    }


    @Override
    public CollectionModel<TagModel> toCollectionModel(Iterable<? extends TagDto> tagDtos) {
        CollectionModel<TagModel> collectionModel = super.toCollectionModel(tagDtos);
        collectionModel.add(linkTo(methodOn(TagController.class).createTag(null))
                .withRel("create"));
        return collectionModel;
    }
}
