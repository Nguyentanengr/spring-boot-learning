package com.anonymous.login_web_application.mapper;

import com.anonymous.login_web_application.dto.request.RoleRequest;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role mapToRole(RoleRequest request);

    @Mapping(target = "permissions", ignore = true)
    Role updateToRole(RoleRequest request, @MappingTarget Role role);

    RoleResponse mapToRoleResponse(Role role);
}
