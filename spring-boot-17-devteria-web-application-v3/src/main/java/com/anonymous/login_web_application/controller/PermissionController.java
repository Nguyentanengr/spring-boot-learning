package com.anonymous.login_web_application.controller;

import com.anonymous.login_web_application.dto.request.PermissionRequest;
import com.anonymous.login_web_application.dto.response.ApiResponse;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.repository.PermissionRepository;
import com.anonymous.login_web_application.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePermission(@PathVariable("id") String id) {
        permissionService.delete(id);
        return ApiResponse.<String>builder()
                .result(ErrorCode.USER_DELETED.getMessage())
                .build();
    }
}
