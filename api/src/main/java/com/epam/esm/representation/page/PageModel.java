package com.epam.esm.representation.page;

import com.epam.esm.service.service.model.Page;
import org.springframework.hateoas.CollectionModel;

public class PageModel<Model> {
    private final CollectionModel<Model> models;
    private final Page page;

    public PageModel(CollectionModel<Model> models, Page page) {
        this.models = models;
        this.page = page;
    }

    public CollectionModel<Model> getModels() {
        return models;
    }

    public Page getPage() {
        return page;
    }
}
