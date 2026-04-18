package com.newsflow.worker.forum.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsflow.worker.forum.domain.ForumEventConsumptionEntity;
import com.newsflow.worker.forum.repository.ForumEventConsumptionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ForumEventConsumer {

    private final ObjectMapper objectMapper;
    private final ForumEventConsumptionRepository consumptionRepository;

    public ForumEventConsumer(ObjectMapper objectMapper, ForumEventConsumptionRepository consumptionRepository) {
        this.objectMapper = objectMapper;
        this.consumptionRepository = consumptionRepository;
    }

    @KafkaListener(
            topics = "${newsflow.kafka.topic-forum-events:forum.events}",
            groupId = "${newsflow.kafka.group-id:cmsaws-worker-forum}"
    )
    @Transactional
    public void onMessage(String message) {
        try {
            ForumEventMessage event = objectMapper.readValue(message, ForumEventMessage.class);

            if (event.eventId() == null || consumptionRepository.existsByEventId(event.eventId())) {
                return;
            }

            ForumEventConsumptionEntity consumption = new ForumEventConsumptionEntity();
            consumption.setEventId(event.eventId());
            consumption.setEventType(event.eventType());
            consumption.setAggregateId(event.aggregateId());
            consumption.setPayload(message);
            consumption.setStatus("PROCESSED");

            consumptionRepository.save(consumption);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Failed to parse forum event message", exception);
        }
    }
}
