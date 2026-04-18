package com.newsflow.contacts.api.mapper;

import com.newsflow.contacts.api.ContactMessageResponse;
import com.newsflow.contacts.domain.ContactMessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper {

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    ContactMessageResponse toResponse(ContactMessageEntity entity);
}
