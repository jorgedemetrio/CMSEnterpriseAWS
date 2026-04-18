package com.newsflow.forum.kafka;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record ForumEvent(
        UUID eventId,
        String eventType,
        UUID aggregateId,
        Instant occurredAt,
        Map<String, Object> payload
) {
}
