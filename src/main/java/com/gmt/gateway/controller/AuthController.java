package com.gmt.gateway.controller;

import com.gmt.gateway.request.LoginRequest;
import com.gmt.gateway.response.ApiResponse;
import com.gmt.gateway.response.LoginResponse;
import com.gmt.gateway.service.AuthenticationService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationService authenticationService;
    private final Gson gson;

    public AuthController(
            final AuthenticationService authenticationService
    ) {
        this.authenticationService = authenticationService;
        this.gson = new Gson();
    }

    @PostMapping("/api/v1/auth/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        logger.info("request: {}", loginRequest);
        ApiResponse<LoginResponse> response = authenticationService.login(loginRequest);
        logger.info("response: {}", response);
        return gson.toJson(response).replace("\"token\"", "\"Auth-Token\""); //TODO: fix serialization
    }

    @PostMapping("/api/v1/auth/loginSMS")
    public String loginSms(@RequestBody LoginRequest loginRequest) {
        logger.info("request: {}", loginRequest);
        ApiResponse<LoginResponse> response = authenticationService.loginSms(loginRequest);
        logger.info("response: {}", response);
        return gson.toJson(response);
    }

}
