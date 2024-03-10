package com.gmt.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CorsFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders responseHeaders = exchange.getResponse().getHeaders();
        responseHeaders.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
        responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

        return chain.filter(exchange);
    }
}
