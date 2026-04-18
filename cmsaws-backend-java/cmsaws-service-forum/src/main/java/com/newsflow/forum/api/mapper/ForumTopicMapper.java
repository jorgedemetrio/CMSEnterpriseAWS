package com.newsflow.forum.api.mapper;

import com.newsflow.forum.api.ForumTopicResponse;
import com.newsflow.forum.domain.ForumTopicEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForumTopicMapper {
    ForumTopicResponse toResponse(ForumTopicEntity entity);
}
