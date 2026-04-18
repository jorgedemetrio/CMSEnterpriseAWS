package com.newsflow.forum.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Component
public class ForumEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic;

    public ForumEventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper,
            @Value("${newsflow.kafka.topic-forum-events:forum.events}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    public void publish(String eventType, UUID aggregateId, Map<String, Object> payload) {
        ForumEvent event = new ForumEvent(
                UUID.randomUUID(),
                eventType,
                aggregateId,
                Instant.now(),
                payload
        );

        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, aggregateId.toString(), json);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Failed to serialize forum event", exception);
        }
    }
}
