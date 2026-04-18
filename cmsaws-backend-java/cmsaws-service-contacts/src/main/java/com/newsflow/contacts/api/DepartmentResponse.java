package com.newsflow.contacts.api;

import java.util.UUID;

public record DepartmentResponse(
        UUID id,
        String name
) {
}