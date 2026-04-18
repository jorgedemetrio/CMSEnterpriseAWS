package com.newsflow.core.api.mapper;

import com.newsflow.core.api.CategoryResponse;
import com.newsflow.core.domain.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(CategoryEntity entity);
}
