package com.newsflow.worker.forum.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForumEventMessage(
        UUID eventId,
        String eventType,
        UUID aggregateId,
        Instant occurredAt,
        Map<String, Object> payload
) {
}
