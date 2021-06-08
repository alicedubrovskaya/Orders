package com.epam.esm.service.dto;

import com.epam.esm.service.entity.enumeration.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    private BigDecimal cost;

    private Currency currency;

    public BigDecimal getCost() {
        return cost;
    }

    public Currency getCurrency() {
        return currency;
    }
}

