package com.newsflow.users.api.mapper;

import com.newsflow.users.api.RoleResponse;
import com.newsflow.users.domain.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toResponse(RoleEntity entity);
}
