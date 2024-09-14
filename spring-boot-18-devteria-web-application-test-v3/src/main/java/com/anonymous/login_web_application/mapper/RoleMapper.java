package com.anonymous.login_web_application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.anonymous.login_web_application.dto.request.RoleRequest;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role mapToRole(RoleRequest request);

    RoleResponse mapToRoleResponse(Role role);
}
