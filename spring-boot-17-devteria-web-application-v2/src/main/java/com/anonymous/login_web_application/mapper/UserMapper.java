package com.anonymous.login_web_application.mapper;

import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User mapToUser(UserCreationRequest request);

    @Mapping(target = "roles", ignore = true)
    User updateToUser(UserUpdateRequest request, @MappingTarget User user);

    UserResponse mapToUserResponse(User user);

}
