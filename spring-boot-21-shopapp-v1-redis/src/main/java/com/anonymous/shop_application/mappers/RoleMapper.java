package com.anonymous.shop_application.mappers;

import com.anonymous.shop_application.dtos.requests.RoleRequest;
import com.anonymous.shop_application.dtos.responses.RoleResponse;
import com.anonymous.shop_application.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role mapToRole(RoleRequest request);

    Role updateToRole(RoleRequest request, @MappingTarget Role role);

    RoleResponse mapToRoleResponse(Role role);
}
