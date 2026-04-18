package com.newsflow.forum.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateForumTopicRequest(
        @NotBlank @Size(max = 200) String title
) {
}
