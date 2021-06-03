package com.epam.esm.representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonRootName(value = "tag")
@Relation(collectionRelation = "tags")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagModel extends RepresentationModel<TagModel> {
    private Long id;

    private String name;
}
