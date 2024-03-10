package com.gmt.gateway.service;

import com.gmt.gateway.response.RecaptchaResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class RecaptchaService {

    private static final Logger logger = LoggerFactory.getLogger(RecaptchaService.class);

    private final Client client;

    @Value("${recaptcha.secret}")
    String recaptchaSecret;

    @Value("${recaptcha.url}")
    String recaptchaValidatorUrl;

    public RecaptchaService(Client jerseyClient) {
        this.client = jerseyClient;
    }

    public boolean validateRecaptcha(String recaptcha) {
        logger.info("recaptcha: {}", recaptcha);
        URI uri = UriBuilder
                .fromUri(recaptchaValidatorUrl)
                .queryParam("secret", recaptchaSecret)
                .queryParam("response", recaptcha)
                .queryParam("remoteip", "1.2.3.4")
                .build();

        RecaptchaResponse response = client
                .target(uri)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(RecaptchaResponse.class);

        logger.info("response: {}", response);

        return response.success();
    }

}
