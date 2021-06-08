package com.epam.esm.service.service.validator;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.entity.enumeration.ErrorMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TagValidator implements Validator<TagDto> {
    private final List<ErrorMessage> errors = new ArrayList<>();
    private static final String CORRECT_TAG = "^[a-z0-9\\_]+$";

    @Override
    public void validate(TagDto tagDto) {
        validateDto(tagDto);
        if (errors.isEmpty()) {
            validateName(tagDto.getName());
        }
    }

    protected void validateDto(TagDto tagDto) {
        if (tagDto == null) {
            errors.add(ErrorMessage.TAG_DTO_EMPTY);
        }
    }

    protected void validateName(String name) {
        if (name == null) {
            errors.add(ErrorMessage.TAG_NAME_EMPTY);
        }

        if (name != null && (!(name.length() > 1 && name.length() <= 255) || !name.matches(CORRECT_TAG))) {
            errors.add(ErrorMessage.TAG_NAME_INCORRECT);
        }
    }

    @Override
    public List<ErrorMessage> getMessages() {
        return errors;
    }
}
