package com.gmt.gateway.client;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.MessageBodyWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public abstract class JerseyBodyWriter<T> implements MessageBodyWriter<T> {

    MediaType mediaType;

    public JerseyBodyWriter(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return this.mediaType.isCompatible(mediaType);
    }

}
