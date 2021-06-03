package com.epam.esm.service.dto.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

public class DurationSerializer extends JsonSerializer<Duration> {

    @Override
    public void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String str = String.valueOf(value.toDays());
        gen.writeString(str);
    }
}
