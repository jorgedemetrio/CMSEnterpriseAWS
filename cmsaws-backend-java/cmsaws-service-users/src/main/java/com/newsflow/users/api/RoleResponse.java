package com.newsflow.users.api;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        Integer statusDado
) {
}
