package com.epam.esm.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleService {
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    public String getLocaleMessage(String messageName, Object... args) {
        return messageSource.getMessage(messageName, args, getLocale());
    }
}
