package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserUpdateRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
    //User toUser(UserUpdateRequest userUpdateRequest);
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
    //@Mapping(source = "firstName", target = "latName")
    //@Mapping(target = "firstName", ignore = true)
    UserResponse toUserResponse(User user);
}
