package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {

    @Min(value = 1, message = "{min_page}")
    private Integer page;

    @Min(value = 1, message = "{min_size_of_page}")
    @Max(value = 100, message = "{max_size_of_page}")
    private Integer size;
}

