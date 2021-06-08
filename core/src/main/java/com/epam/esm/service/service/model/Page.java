package com.epam.esm.service.service.model;

import lombok.Data;

@Data
public class Page {
    private final long totalNumberOfElements;
    private final long totalNumberOfPages;

    public Page(long totalNumberOfElements, long totalNumberOfPages) {
        this.totalNumberOfElements = totalNumberOfElements;
        this.totalNumberOfPages = totalNumberOfPages;
    }
}
