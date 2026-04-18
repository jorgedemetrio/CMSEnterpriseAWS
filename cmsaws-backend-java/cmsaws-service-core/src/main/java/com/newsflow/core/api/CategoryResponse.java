package com.newsflow.core.api;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name
) {
}