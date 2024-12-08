package com.ducgiang.identify_service.mapper;

import com.ducgiang.identify_service.dto.request.RoleRequest;
import com.ducgiang.identify_service.dto.response.RoleResponse;
import com.ducgiang.identify_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}