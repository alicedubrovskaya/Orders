package com.epam.esm.service.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private Long id;

    private String name;
}
