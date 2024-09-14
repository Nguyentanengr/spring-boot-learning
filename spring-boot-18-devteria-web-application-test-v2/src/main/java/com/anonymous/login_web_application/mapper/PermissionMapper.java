package com.anonymous.login_web_application.mapper;

import org.mapstruct.Mapper;

import com.anonymous.login_web_application.dto.request.PermissionRequest;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission mapToPermission(PermissionRequest request);

    PermissionResponse mapToPermissionResponse(Permission permission);
}
