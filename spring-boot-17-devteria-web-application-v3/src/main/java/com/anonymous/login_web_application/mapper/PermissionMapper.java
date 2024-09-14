package com.anonymous.login_web_application.mapper;

import com.anonymous.login_web_application.dto.request.PermissionRequest;
import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.entity.Permission;
import com.anonymous.login_web_application.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission mapToPermission(PermissionRequest request);

    Permission updateToPermission(PermissionRequest request, @MappingTarget Permission permission);

    PermissionResponse mapToPermissionResponse(Permission permission);

}
