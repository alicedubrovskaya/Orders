package com.epam.esm.service.dto.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@JsonComponent
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'").withZone(ZoneOffset.UTC);

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return LocalDateTime.from(dateTimeFormatter.parse(p.getText()));
    }
}
