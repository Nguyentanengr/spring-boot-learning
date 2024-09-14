package com.anonymous.login_web_application.service;

import com.anonymous.login_web_application.dto.request.RoleRequest;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.entity.Role;
import com.anonymous.login_web_application.mapper.RoleMapper;
import com.anonymous.login_web_application.repository.PermissionRepository;
import com.anonymous.login_web_application.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {

    RoleRepository roleRepository;

    PermissionRepository permissionRepository;

    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {

        Role role = roleMapper.mapToRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.mapToRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::mapToRoleResponse)
                .collect(Collectors.toList());
    }

    public void delete(String id) {
        roleRepository.deleteById(id);
    }
}
