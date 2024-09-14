package com.anonymous.login_web_application.service;

import com.anonymous.login_web_application.dto.request.PermissionRequest;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.entity.Permission;
import com.anonymous.login_web_application.exception.AppException;
import com.anonymous.login_web_application.exception.DBException;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.mapper.PermissionMapper;
import com.anonymous.login_web_application.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::mapToPermissionResponse)
                .collect(Collectors.toList());
    }

    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.PERMISSION_EXISTED);

        Permission permission = permissionMapper.mapToPermission(request);
        return permissionMapper.mapToPermissionResponse(permissionRepository.save(permission));
    }

    public void deletePermission(String id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        try {
            permissionRepository.delete(permission);
        } catch (Exception e) {
            throw new DBException(ErrorCode.PERMISSION_FOREIGN_KEY_VIOLATED);
        }
    }
}
