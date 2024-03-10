package com.gmt.gateway.client;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gmt.gateway.response.ApiResponse;
import com.gmt.gateway.util.JsonUtil;

import java.io.IOException;

//public class ApiResponseSerializer<T> extends JsonSerializer<ApiResponse<T>> {
//    private static final ObjectMapper mapper = JsonUtil.getDefaultObjectMapper();
//
//    @Override
//    public void serialize(ApiResponse<T> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        gen.writeStartObject();
//
//
//        final T result;
//        final ApiResponse.Error[] errors;
//        final String errorMessage;
//        final ApiResponse.ErrorCode errorCode;
//
//
//        gen.writeEndObject();
//    }
