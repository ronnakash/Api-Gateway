package com.gmt.gateway.filter;

import com.gmt.gateway.service.JwtTokenService;
import com.gmt.gateway.util.HeadersUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class JwtAuthorizationFilter implements GatewayFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final JwtTokenService jwtTokenService;

    public JwtAuthorizationFilter(
            final JwtTokenService jwtTokenService
    ) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        Optional<String> jwt = HeadersUtil.getHeader(requestHeaders, "signature"); //should return 403
        Optional<String> authToken = HeadersUtil.getHeader(requestHeaders, "Auth-Token"); //should return 403

        if (authToken.isEmpty() || jwt.isEmpty() || !jwtTokenService.validateToken(jwt.get(), authToken.get())){
            logger.error("failed to validate signature for auth token {}", authToken);
            return Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
            });
        }

        logger.info("validated signature for auth token {}, signature {}", authToken, jwt);
        return chain.filter(exchange);
    }
}