package com.gmt.gateway.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class JsonUtil {

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    public static Optional<String> getText(JsonNode node, String key) {
        JsonNode value = node.get(key);
        if (value == null)
            return Optional.empty();
        return Optional.of(value.asText());
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return DEFAULT_OBJECT_MAPPER;
    }
}
