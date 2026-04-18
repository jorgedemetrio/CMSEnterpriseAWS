package com.newsflow.forum.api;

import java.util.UUID;

public record ForumPostResponse(
        UUID id,
        UUID topicId,
        String topicTitle,
        String authorName,
        String content
) {
}