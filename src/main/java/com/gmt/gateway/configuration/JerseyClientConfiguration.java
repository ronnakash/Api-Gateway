package com.gmt.gateway.configuration;

import com.gmt.gateway.client.JacksonBodyReader;
import com.gmt.gateway.client.JacksonBodyWriter;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JerseyClientConfiguration {

    @Bean
    public Client jerseyClient() {
        return ClientBuilder
                .newBuilder()
                .register(new JacksonBodyReader<>(MediaType.APPLICATION_JSON_TYPE))
                .register(new JacksonBodyWriter<>(MediaType.APPLICATION_JSON_TYPE))
                .build();
    }

}
