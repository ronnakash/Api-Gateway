package com.gmt.gateway.request;

public record LoginRequest(
        String country,
        String identification,
        String signature,
        String mobileNumber,
        String jwt,
        String smsCode,
        String recaptchaToken,
        String userFirstName,
        boolean biometricSign,
        String timestamp
) {}
