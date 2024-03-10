package com.gmt.gateway.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class JacksonBodyWriter<T> extends JerseyBodyWriter<T>{

    protected ObjectMapper objectMapper;

    public JacksonBodyWriter(MediaType mediaType, ObjectMapper objectMapper) {
        this(mediaType);
        this.objectMapper = objectMapper;
    }

    public JacksonBodyWriter(MediaType mediaType) {
        super(mediaType);
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        Writer writer = new OutputStreamWriter(entityStream, StandardCharsets.UTF_8);
        objectMapper.writeValue(writer, t);
    }
}
