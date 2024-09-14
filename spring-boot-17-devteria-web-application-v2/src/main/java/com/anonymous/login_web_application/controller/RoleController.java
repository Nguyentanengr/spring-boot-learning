package com.anonymous.login_web_application.controller;

import com.anonymous.login_web_application.dto.request.PermissionRequest;
import com.anonymous.login_web_application.dto.request.RoleRequest;
import com.anonymous.login_web_application.dto.response.ApiResponse;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.service.PermissionService;
import com.anonymous.login_web_application.service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> createPermission(@RequestBody @Valid RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllPermissions() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePermission(@PathVariable("id") String id) {
        roleService.delete(id);
        return ApiResponse.<String>builder()
                .result(ErrorCode.USER_DELETED.getMessage())
                .build();
    }
}
