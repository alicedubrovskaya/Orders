package com.epam.esm.representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonRootName(value = "order")
@Relation(collectionRelation = "orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderModel extends RepresentationModel<OrderModel> {
    private Long id;

    private PriceModel price;

    private LocalDateTime purchaseDate;

    private CertificateModel certificate;
}
