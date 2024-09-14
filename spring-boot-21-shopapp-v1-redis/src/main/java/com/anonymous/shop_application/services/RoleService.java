package com.anonymous.shop_application.services;

import com.anonymous.shop_application.dtos.requests.RoleRequest;
import com.anonymous.shop_application.dtos.responses.RoleResponse;
import com.anonymous.shop_application.exceptions.AppException;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.mappers.RoleMapper;
import com.anonymous.shop_application.models.Role;
import com.anonymous.shop_application.repositories.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {

    RoleRepository roleRepository;

    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.mapToRole(request);
        Role created = roleRepository.save(role);

        return roleMapper.mapToRoleResponse(created);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::mapToRoleResponse)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Role role = findRoleById(id);
        roleRepository.delete(role);
    }

    public Role findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }
}
