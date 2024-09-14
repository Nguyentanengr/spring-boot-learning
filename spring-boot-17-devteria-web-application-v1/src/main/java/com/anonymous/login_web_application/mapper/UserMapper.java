package com.anonymous.login_web_application.mapper;

import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToUser(UserCreationRequest request);

    User updateToUser(UserUpdateRequest request, @MappingTarget User user);

    UserResponse mapToUserResponse(User user);

}
