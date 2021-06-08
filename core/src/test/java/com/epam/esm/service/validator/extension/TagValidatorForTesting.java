package com.epam.esm.service.validator.extension;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import com.epam.esm.service.service.validator.TagValidator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TagValidatorForTesting extends TagValidator {
    @Override
    public void validate(TagDto tagDto) {
        super.validate(tagDto);
    }

    @Override
    public void validateDto(TagDto tagDto) {
        super.validateDto(tagDto);
    }

    @Override
    public void validateName(String name) {
        super.validateName(name);
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return super.getMessages();
    }
}
