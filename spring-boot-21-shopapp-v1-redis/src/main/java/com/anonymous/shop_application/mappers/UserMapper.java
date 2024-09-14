package com.anonymous.shop_application.mappers;

import com.anonymous.shop_application.dtos.requests.UserRequest;
import com.anonymous.shop_application.dtos.responses.UserResponse;
import com.anonymous.shop_application.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User mapToUser(UserRequest request);

    @Mapping(target = "roles", ignore = true)
    User updateToUser(UserRequest request, @MappingTarget User user);

    UserResponse mapToUserResponse(User user);

}
