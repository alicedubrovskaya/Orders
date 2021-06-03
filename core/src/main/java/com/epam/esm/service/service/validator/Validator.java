package com.epam.esm.service.service.validator;


import com.epam.esm.service.entity.enumeration.ErrorMessage;

import java.util.List;

public interface Validator<Entity> {
    void validate(Entity entity);

    List<ErrorMessage> getMessages();
}
