package com.gmt.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//public class SecurityConfiguration {
////    @Autowired
////    private JwtAuthenticationEntryPoint point;
////    @Autowired
////    private JwtAuthenticationFilter filter;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf
//                        .disable())
//                .authorizeRequests()
//                .requestMatchers("/auth/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
////                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
////        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}
