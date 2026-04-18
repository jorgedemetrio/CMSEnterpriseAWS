package com.newsflow.users.api;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String name,
        String email,
        String roleName
) {
}
