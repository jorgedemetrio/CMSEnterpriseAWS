package com.newsflow.core.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateArticleRequest(
        @NotBlank @Size(max = 250) String title,
        @NotBlank String content,
        @NotNull UUID categoryId,
        boolean isHighlight
) {
}
