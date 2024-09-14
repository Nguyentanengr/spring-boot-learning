package com.anonymous.login_web_application.controller;

import java.util.List;

import jakarta.validation.Valid;

import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.ApiResponse;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(
            @PathVariable("id")
            @Min(value = 1, message = "USER_ID_INVALID") Long id) {
        return ApiResponse.<UserResponse>builder()
                .value(userService.getUserById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .value(userService.getAllUsers())
                .build();
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .value(userService.createUser(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @RequestBody @Valid UserUpdateRequest request, @PathVariable("id") Long id) {
        return ApiResponse.<UserResponse>builder()
                .value(userService.updateUser(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .value(userService.getMyInfo())
                .build();
    }
}
