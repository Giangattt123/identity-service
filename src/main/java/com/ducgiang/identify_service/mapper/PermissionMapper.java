package com.ducgiang.identify_service.mapper;

import com.ducgiang.identify_service.dto.request.PermissionRequest;
import com.ducgiang.identify_service.dto.request.UserCreationRequest;
import com.ducgiang.identify_service.dto.request.UserUpdateRequest;
import com.ducgiang.identify_service.dto.response.PermissionResponse;
import com.ducgiang.identify_service.dto.response.UserResponse;
import com.ducgiang.identify_service.entity.Permission;
import com.ducgiang.identify_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
//    @Mapping(source = "lastName" , ignore = true) -> not map field lastName
//    @Mapping(source = "firstName" , target = "lastName")
//    UserResponse toUserResponse(User user);
//    void updateUser(@MappingTarget User user , UserUpdateRequest request);
}
