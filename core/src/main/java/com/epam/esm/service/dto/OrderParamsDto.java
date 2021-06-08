package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderParamsDto {
    @Min(value = 1, message = "{valid_id}")
    private Long userId;

    @Min(value = 1, message = "{valid_id}")
    private Long certificateId;
}
