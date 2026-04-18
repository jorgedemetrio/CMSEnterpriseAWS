package com.newsflow.users.api;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        UUID roleId,
        String roleName,
        Integer statusDado
) {
}