package com.anonymous.shop_application.services;

import com.anonymous.shop_application.dtos.requests.UserRequest;
import com.anonymous.shop_application.dtos.responses.UserListResponse;
import com.anonymous.shop_application.dtos.responses.UserResponse;
import com.anonymous.shop_application.exceptions.AppException;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.mappers.UserMapper;
import com.anonymous.shop_application.models.User;
import com.anonymous.shop_application.repositories.RoleRepository;
import com.anonymous.shop_application.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserMapper userMapper;
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ObjectMapper objectMapper;
    RedisTemplate<String, String> redisTemplate;

    public UserResponse getUserById(Long id) throws JsonProcessingException {
        // search in cache
        String cacheKey = "User::" + "id::" + id;
        Optional<String> cachedValue = Optional.ofNullable( redisTemplate.opsForValue().get(cacheKey));
        if (cachedValue.isPresent()) {
            return objectMapper.readValue(cachedValue.get(), UserResponse.class);
        }

        // search in database and cache
        User user = findUserById(id);
        UserResponse userResponse = userMapper.mapToUserResponse(user);

        redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(userResponse));
        return userResponse;
    }


    public UserListResponse getAllUsers(String keyword, Pageable pageable) throws JsonProcessingException {

        // search in cache
        String cacheKey = "User::"
                + "keyword::" + keyword + "::"
                + "pageNumber::" + pageable.getPageNumber() + "::"
                + "pageSize::" + pageable.getPageSize();

        Optional<String> cachedValue = Optional.ofNullable(redisTemplate.opsForValue().get(cacheKey));
        if (cachedValue.isPresent()) {
            return objectMapper.readValue(cachedValue.get(), UserListResponse.class);
        }

        // search in database and cache
        Page<User> page = userRepository.findAllByFilter(keyword, pageable);
        UserListResponse userListResponse = UserListResponse.builder()
                .users(page
                        .map(userMapper::mapToUserResponse)
                        .stream().collect(Collectors.toList()))
                .totalPages(page.getTotalPages())
                .build();

        // cache with timeout
        Duration timeout = Duration.ofSeconds(10);
        redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(userListResponse), timeout);

        return userListResponse;
    }

    public UserResponse createUser(UserRequest request) {

        if (!request.getPassword().equals(request.getRetypePassword()))
            throw new AppException(ErrorCode.USER_PASSWORD_NOT_MATCH);

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        if (request.getRoles().size() != roles.size())
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        user.setRoles(new HashSet<>(roles));

        user.setIsActive(1);
        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        if (!request.getPassword().equals(request.getRetypePassword()))
            throw new AppException(ErrorCode.USER_PASSWORD_NOT_MATCH);

        User user = findUserById(id);

        userMapper.updateToUser(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        if (request.getRoles().size() != roles.size())
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        user.setRoles(new HashSet<>(roles));

        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    public String blockOrEnableUser(Long id, int active) {
        if (!(0 <= active && active <= 1))
            throw new AppException(ErrorCode.USER_ACTIVE_INVALID);

        User user = findUserById(id);

        user.setIsActive(active);
        userRepository.save(user);
        return active > 0 ? "Successfully enabled the user" : "Successfully blocked the user";
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public boolean existsByName(String fullName) {
        return userRepository.existsByFullName(fullName);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
    /*
    login
    logout
    refresh token
    get user details
    update user details
    reset password
     */

}
