package com.anonymous.login_web_application.service;

import com.anonymous.login_web_application.dto.request.RoleRequest;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.entity.Role;
import com.anonymous.login_web_application.exception.AppException;
import com.anonymous.login_web_application.exception.DBException;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.mapper.RoleMapper;
import com.anonymous.login_web_application.repository.PermissionRepository;
import com.anonymous.login_web_application.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;

    PermissionRepository permissionRepository;

    RoleMapper roleMapper;

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::mapToRoleResponse)
                .collect(Collectors.toList());
    }

    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.ROLE_EXISTED);

        Role role = roleMapper.mapToRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        if (request.getPermissions().size() != permissions.size())
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.mapToRoleResponse(roleRepository.save(role));
    }

    public void deleteRole(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        try {
            roleRepository.delete(role);
        } catch (Exception e) {
            throw new DBException(ErrorCode.ROLE_FOREIGN_KEY_VIOLATED);
        }
    }
}
