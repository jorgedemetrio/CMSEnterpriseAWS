package com.newsflow.contacts.api;

import java.time.Instant;
import java.util.List;

public record ApiError(
        int status,
        String error,
        String message,
        Instant timestamp,
        String path,
        List<String> details
) {
}
