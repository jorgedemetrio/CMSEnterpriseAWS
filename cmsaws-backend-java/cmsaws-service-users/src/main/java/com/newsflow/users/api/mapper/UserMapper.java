package com.newsflow.users.api.mapper;

import com.newsflow.users.api.UserResponse;
import com.newsflow.users.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "roleName", source = "role.name")
    UserResponse toResponse(UserEntity entity);
}
