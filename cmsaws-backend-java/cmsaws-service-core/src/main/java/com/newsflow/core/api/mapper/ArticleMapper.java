package com.newsflow.core.api.mapper;

import com.newsflow.core.api.ArticleResponse;
import com.newsflow.core.domain.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "isHighlight", expression = "java(entity.isHighlight())")
    ArticleResponse toResponse(ArticleEntity entity);
}
