package com.anonymous.login_web_application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User mapToUser(UserCreationRequest request);

    @Mapping(target = "roles", ignore = true)
    User updateToUser(UserUpdateRequest request, @MappingTarget User user);

    UserResponse mapToUserResponse(User user);
}
