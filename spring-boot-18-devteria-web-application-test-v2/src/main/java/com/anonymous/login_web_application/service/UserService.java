package com.anonymous.login_web_application.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.entity.User;
import com.anonymous.login_web_application.exception.AppException;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.mapper.UserMapper;
import com.anonymous.login_web_application.repository.RoleRepository;
import com.anonymous.login_web_application.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserMapper userMapper;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;

    // ONLY CURRENT USER GET USER'S INFO
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.mapToUserResponse(user);
    }

    // ROLE ADMIN : ADMIN MANAGE
//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    // NO AUTHENTICATION
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        User user = userMapper.mapToUser(request);

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    // ROLE ADMIN : ADMIN MANAGE
    public UserResponse updateUser(UserUpdateRequest request, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!request.getUsername().equals(user.getUsername())) {
            userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
                throw new AppException(ErrorCode.USERNAME_EXISTED);
            });
        }

        userMapper.updateToUser(request, user);

        var roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    // ROLE ADMIN : ADMIN MANAGE
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    // ANY ROLE
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext(); // return SecurityContext
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.mapToUserResponse(user);
    }
}
