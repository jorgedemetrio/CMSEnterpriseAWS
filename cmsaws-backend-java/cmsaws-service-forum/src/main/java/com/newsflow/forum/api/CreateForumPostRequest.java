package com.newsflow.forum.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateForumPostRequest(
        @NotNull UUID topicId,
        @NotBlank @Size(max = 150) String authorName,
        @NotBlank String content
) {
}
