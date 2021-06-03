package com.epam.esm.service.service.model;

public class Page {
    private final long totalNumberOfElements;
    private final long totalNumberOfPages;

    public Page(long totalNumberOfElements, long totalNumberOfPages) {
        this.totalNumberOfElements = totalNumberOfElements;
        this.totalNumberOfPages = totalNumberOfPages;
    }

    public long getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    public long getTotalNumberOfPages() {
        return totalNumberOfPages;
    }
}
