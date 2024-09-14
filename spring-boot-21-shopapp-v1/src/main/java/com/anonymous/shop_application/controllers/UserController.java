package com.anonymous.shop_application.controllers;


import com.anonymous.shop_application.dtos.requests.UserRequest;
import com.anonymous.shop_application.dtos.responses.ApiResponse;
import com.anonymous.shop_application.dtos.responses.UserListResponse;
import com.anonymous.shop_application.dtos.responses.UserResponse;
import com.anonymous.shop_application.services.UserService;
import com.github.javafaker.Faker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User controller")
public class UserController {

    UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get user detail", description = "API get user detail")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") Long id) {

        return ApiResponse.<UserResponse>builder()
                .value(userService.getUserById(id))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get user list per page", description = "API get user list per page")
    public ApiResponse<UserListResponse> getAllUsers(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return ApiResponse.<UserListResponse>builder()
                .value(userService.getAllUsers(keyword, pageRequest))
                .build();
    }

    @PostMapping("/register") // dang ky tai khoan user
    @Operation(summary = "Register new user", description = "API register new user")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .value(userService.createUser(request))
                .build();
    }

    @PutMapping("/{id}") // cap nhat tai khoan
    @Operation(summary = "Update user", description = "API update user")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable("id") Long id,
            @RequestBody @Valid UserRequest request)
    {
        return ApiResponse.<UserResponse>builder()
                .value(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("/{id}/{active}") // block tai khoan
    @Operation(summary = "Block or enable user", description = "API block or enable user")
    public ApiResponse<String> blockOrEnableUser(
            @PathVariable("id") Long id,
            @PathVariable("active") int active)
    {
        return ApiResponse.<String>builder()
                .value(userService.blockOrEnableUser(id, active))
                .build();
    }

    @PostMapping("/generateFakeUsers")
    @Operation(summary = "Generate fake user list", description = "API generate fake user list")
    private ApiResponse<Void> generateFakeUsers() {
        Faker faker = new Faker();
        for (int i = 0; i < 6_000; i++) {
            String fullName = faker.name().fullName();
            if (userService.existsByName(fullName)) {
                continue;
            }
            String phoneNumber = "09" + faker.number().digits(8);

            if (userService.existsByPhoneNumber(phoneNumber)) {
                continue;
            }
            String password = faker.internet().password(8, 100);
            UserRequest userRequest = UserRequest.builder()
                    .fullName(fullName)
                    .phoneNumber(phoneNumber)
                    .address(faker.address().fullAddress())
                    .password(password)
                    .retypePassword(password)
                    .dayOfBirth(convertToLocalDate(faker.date().birthday(18, 25)))
                    .roles(new HashSet<>(List.of(faker.number().numberBetween(1L, 3L))))
                    .build();

            userService.createUser(userRequest);
        }
        return ApiResponse.<Void>builder()
                .message("Successfully generate fake products")
                .build();
    }

    public static LocalDate convertToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
