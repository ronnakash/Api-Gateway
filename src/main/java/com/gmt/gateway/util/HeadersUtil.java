package com.gmt.gateway.util;

import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Optional;

public class HeadersUtil {

    public static Optional<String> getHeader(HttpHeaders headers, String headerName) {
        List<String> headerValues = headers.get(headerName);
        if (headerValues == null || headerValues.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(headerValues.get(0));
    }
}
