package com.epam.esm.service.entity;

import com.epam.esm.service.entity.enumeration.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private BigDecimal cost;
    private Currency currency;
}
