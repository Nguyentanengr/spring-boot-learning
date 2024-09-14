package com.anonymous.login_web_application.service;

import com.anonymous.login_web_application.dto.request.PermissionRequest;
import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.entity.Permission;
import com.anonymous.login_web_application.entity.User;
import com.anonymous.login_web_application.enums.Role;
import com.anonymous.login_web_application.exception.AppException;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.mapper.PermissionMapper;
import com.anonymous.login_web_application.mapper.UserMapper;
import com.anonymous.login_web_application.repository.PermissionRepository;
import com.anonymous.login_web_application.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {

    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {

        Permission permission = permissionMapper.mapToPermission(request);

        return permissionMapper.mapToPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAll() {

        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::mapToPermissionResponse)
                .collect(Collectors.toList());
    }

    public void delete(String id) {

        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        permissionRepository.delete(permission);
    }

}
