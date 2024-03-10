package com.gmt.gateway.client;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.MessageBodyReader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public abstract class JerseyBodyReader<T> implements MessageBodyReader<T> {

    MediaType mediaType;

    JerseyBodyReader(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
//        return this.mediaType.isCompatible(mediaType);
        return true;
    }
}