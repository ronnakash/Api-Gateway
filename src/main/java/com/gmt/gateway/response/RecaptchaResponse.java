package com.gmt.gateway.response;

public record RecaptchaResponse (
        boolean success,
        String challenge_ts,
        String hostname,
        double score
) {}
