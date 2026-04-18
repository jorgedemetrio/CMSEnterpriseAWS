package com.newsflow.contacts.api;

import java.util.UUID;

public record ContactMessageResponse(
        UUID id,
        UUID departmentId,
        String departmentName,
        String name,
        String email,
        String message
) {
}