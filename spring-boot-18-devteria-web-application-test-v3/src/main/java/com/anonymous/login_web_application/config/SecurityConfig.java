package com.anonymous.login_web_application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.anonymous.login_web_application.dto.response.ApiResponse;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINT = {
        "/users", "/roles", "/permissions", "/auth/login", "/auth/logout", "/auth/refresh",
    };

    @Lazy
    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT)
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .oauth2ResourceServer(auth -> {
                    auth.jwt(jwtConfigurer ->
                                    jwtConfigurer.decoder(customJwtDecoder).jwtAuthenticationConverter(converter()))
                            .authenticationEntryPoint((request, response, authException) -> {
                                ErrorCode errorCode = ErrorCode.USER_UNAUTHENTICATED;
                                response.setStatus(errorCode.getHttpStatusCode().value());
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                                ApiResponse<?> apiResponse = ApiResponse.builder()
                                        .code(errorCode.getCode())
                                        .message(errorCode.getMessage())
                                        .build();

                                ObjectMapper objectMapper = new ObjectMapper();

                                response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
                                response.flushBuffer();
                            });
                })
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    JwtAuthenticationConverter converter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
