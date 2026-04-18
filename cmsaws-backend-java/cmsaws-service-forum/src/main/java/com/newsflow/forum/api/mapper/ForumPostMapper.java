package com.newsflow.forum.api.mapper;

import com.newsflow.forum.api.ForumPostResponse;
import com.newsflow.forum.domain.ForumPostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ForumPostMapper {

    @Mapping(target = "topicId", source = "topic.id")
    @Mapping(target = "topicTitle", source = "topic.title")
    ForumPostResponse toResponse(ForumPostEntity entity);
}
