package com.epam.esm.representation;

import com.epam.esm.service.entity.enumeration.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@JsonRootName(value = "price")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceModel {
    private BigDecimal cost;

    private Currency currency;
}
