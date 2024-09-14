package com.anonymous.login_web_application.configuration;

import com.anonymous.login_web_application.dto.response.ApiResponse;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // phan quyen tren anotation
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {"/users", "/auth/login", "/auth/introspect", "/permissions", "/auth/logout", "/auth/refresh"};

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Lazy
    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // Cau hinh endpoint nao can duoc bao ve
        // endpoint dang ky va dang nhap phai duoc public

        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll() // cap quyen cho tat ca users
                        .anyRequest().authenticated()
        );

        httpSecurity.oauth2ResourceServer(oauth ->
                oauth.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
                        .jwtAuthenticationConverter(converter()))
                        .authenticationEntryPoint(((request, response, authException) -> {
                            ErrorCode errorCode = ErrorCode.USER_UNAUTHENTICATED;
                            response.setStatus(errorCode.getStatusCode().value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                            ApiResponse<?> apiResponse = ApiResponse.builder()
                                    .code(errorCode.getCode())
                                    .message(errorCode.getMessage())
                                    .build();

                            ObjectMapper objectMapper = new ObjectMapper();

                            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
                            response.flushBuffer();
                        }))
        );

        // spring security se tu dong bat cau hinh csrf de ngan chan cac request khong ro rang
        // -> do do, config o tren duoc xem la vo nghia.
        // co the bo config nay de dong bo voi config requestMatchers

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();


    }

    @Bean
    JwtAuthenticationConverter converter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

//    @Bean
//    JwtDecoder jwtDecoder() {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
