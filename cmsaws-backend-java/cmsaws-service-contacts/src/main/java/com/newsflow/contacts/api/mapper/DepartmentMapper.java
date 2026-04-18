package com.newsflow.contacts.api.mapper;

import com.newsflow.contacts.api.DepartmentResponse;
import com.newsflow.contacts.domain.DepartmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentResponse toResponse(DepartmentEntity entity);
}
