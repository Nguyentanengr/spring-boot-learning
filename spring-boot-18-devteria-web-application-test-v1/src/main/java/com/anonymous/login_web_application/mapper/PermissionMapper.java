package com.anonymous.login_web_application.mapper;

import com.anonymous.login_web_application.dto.request.PermissionRequest;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission mapToPermission(PermissionRequest request);

    PermissionResponse mapToPermissionResponse(Permission permission);
}
