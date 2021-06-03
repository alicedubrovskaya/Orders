package com.epam.esm.representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonRootName(value = "certificate")
@Relation(collectionRelation = "certificates")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificateModel extends RepresentationModel<CertificateModel> {
    private Long id;

    private String name;

    private String description;

    private PriceModel price;

    private Duration duration;

    private LocalDateTime dateOfCreation;

    private LocalDateTime dateOfModification;

    private List<TagModel> tags = new ArrayList<>();
}
