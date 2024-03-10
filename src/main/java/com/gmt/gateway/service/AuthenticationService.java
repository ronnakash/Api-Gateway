package com.gmt.gateway.service;

import com.gmt.gateway.request.LoginRequest;
import com.gmt.gateway.response.ApiResponse;
import com.gmt.gateway.response.CoreLoginResponse;
import com.gmt.gateway.response.LoginResponse;
import com.gmt.gateway.response.UserDetails;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;


@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final RecaptchaService recaptchaService;
    private final JwtTokenService jwtTokenService;
    private final Client client;
    private final ConcurrentMap<String, String> smsCache;

    @Value("${core.url}")
    private String coreUrl;

    public AuthenticationService(
            final RecaptchaService recaptchaService,
            final JwtTokenService jwtTokenService,
            final Client jerseyClient
    ) {
        this.recaptchaService = recaptchaService;
        this.jwtTokenService = jwtTokenService;
        this.client = jerseyClient;
        this.smsCache = ExpiringMap.builder()
                .expiration(5, TimeUnit.MINUTES)
                .maxSize(1000)
                .build();
    }

    public ApiResponse<LoginResponse> login(LoginRequest loginRequest) {
        String smsCode = smsCache.get(loginRequest.identification());
        if (smsCode == null) {
            return ApiResponse.error(ApiResponse.ErrorCode.SMS_EXPIRED);
        }
        if (!smsCode.equals(loginRequest.smsCode())) {
            return ApiResponse.error(ApiResponse.ErrorCode.SMS_WRONG);
        }

        Entity<LoginRequest> loginRequestEntity = Entity.entity(loginRequest, MediaType.APPLICATION_JSON_TYPE);
        CoreLoginResponse response = client
                .target(coreUrl + "/api/v1/auth/gatewayLogin")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(loginRequestEntity, CoreLoginResponse.class);

        UserDetails userDetails = response.getUserDetails();

        logger.info("response: {}", response);
        String token = jwtTokenService.generateToken(userDetails, response.getAuthToken());
        logger.info("created token {} for userDetails {}", token, userDetails.identifier());

        return ApiResponse.ok(LoginResponse.userDetailsResponse(userDetails, token, new Date().getTime(), response.getAuthToken()));
    }

    public ApiResponse<LoginResponse> loginSms(LoginRequest loginRequest) {
        boolean isRecaptchaValid = recaptchaService.validateRecaptcha(loginRequest.recaptchaToken());
        if (!isRecaptchaValid) {
            return ApiResponse.error(ApiResponse.ErrorCode.CAPTCHA_NOT_VERIFIED);
        }

        logger.info("validated recaptcha");

        Entity<LoginRequest> loginRequestEntity = Entity.entity(loginRequest, MediaType.APPLICATION_JSON_TYPE);
        CoreLoginResponse response = client
                .target(coreUrl + "/api/v1/auth/gatewayLoginSms")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(loginRequestEntity, CoreLoginResponse.class);

        smsCache.put(loginRequest.identification(), response.getSmsCode());
        logger.info("response: {}", response);

        return ApiResponse.ok(LoginResponse.smsResponse("", new Date().getTime()));
    }

}
