package com.newsflow.core.api;

import java.util.UUID;

public record ArticleResponse(
        UUID id,
        String title,
        String content,
        UUID categoryId,
        String categoryName
) {
}