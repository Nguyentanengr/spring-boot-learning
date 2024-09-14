package com.anonymous.shop_application.controllers;

import com.anonymous.shop_application.dtos.requests.RoleRequest;
import com.anonymous.shop_application.dtos.responses.ApiResponse;
import com.anonymous.shop_application.dtos.responses.RoleResponse;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.services.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody @Valid RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .value(roleService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .value(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRole(@PathVariable("id") Long id) {
        roleService.delete(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
