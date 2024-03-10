package com.gmt.gateway.configuration;

import com.gmt.gateway.filter.CorsFilter;
import com.gmt.gateway.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayConfiguration {

    private static final int CORS_FILTER_ORDER = Integer.MAX_VALUE;
    private static final int JWT_FILTER_ORDER = 0;

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public GatewayConfiguration(
            final JwtAuthorizationFilter jwtAuthorizationFilter
    ) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Value("${core.url}")
    private String coreUrl;

    @Bean
    public RouteLocator customRouteLocator(
            RouteLocatorBuilder builder
    ) {

        return builder.routes()
                .route(r -> r
                        .alwaysTrue()
                        .filters(f -> f
                                .filter(new CorsFilter(), CORS_FILTER_ORDER))
                        .uri(coreUrl)
                )
                .route(r -> r
                        .path("/api/v1/auth/**")
                        .negate()
                        .filters(f -> f
                                .filter(jwtAuthorizationFilter, JWT_FILTER_ORDER))
                        .uri(coreUrl))
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addExposedHeader(HttpHeaders.SET_COOKIE);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(corsConfigurationSource);
    }

}