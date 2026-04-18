package com.newsflow.forum.api;

import java.util.UUID;

public record ForumTopicResponse(
        UUID id,
        String title
) {
}