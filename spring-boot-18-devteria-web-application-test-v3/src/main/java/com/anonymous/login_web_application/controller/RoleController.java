package com.anonymous.login_web_application.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.anonymous.login_web_application.dto.request.RoleRequest;
import com.anonymous.login_web_application.dto.response.ApiResponse;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .value(roleService.getAllRoles())
                .build();
    }

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody @Valid RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .value(roleService.createRole(request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRole(@PathVariable("id") String id) {
        roleService.deleteRole(id);
        return ApiResponse.<Void>builder().build();
    }
}
