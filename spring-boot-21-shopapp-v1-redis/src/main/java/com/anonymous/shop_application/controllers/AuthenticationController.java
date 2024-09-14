package com.anonymous.shop_application.controllers;

import com.anonymous.shop_application.dtos.requests.AuthenticationRequest;
import com.anonymous.shop_application.dtos.requests.LogoutRequest;
import com.anonymous.shop_application.dtos.responses.ApiResponse;
import com.anonymous.shop_application.dtos.responses.AuthenticationResponse;
import com.anonymous.shop_application.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .value(authenticationService.login(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody @Valid LogoutRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<String>builder()
                .message(authenticationService.logout(request))
                .build();
    }
}
