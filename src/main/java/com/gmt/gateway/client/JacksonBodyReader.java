package com.gmt.gateway.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class JacksonBodyReader<T> extends JerseyBodyReader<T> {

    ObjectMapper objectMapper;

    public JacksonBodyReader(MediaType mediaType, ObjectMapper objectMapper) {
        this(mediaType);
        this.objectMapper = objectMapper;
    }

    public JacksonBodyReader(MediaType mediaType) {
        super(mediaType);
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        Reader reader = new InputStreamReader(entityStream, StandardCharsets.UTF_8);
        return objectMapper.readValue(reader, type);
    }
}
