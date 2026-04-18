package com.newsflow.worker.forum.repository;

import com.newsflow.worker.forum.domain.ForumEventConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ForumEventConsumptionRepository extends JpaRepository<ForumEventConsumptionEntity, UUID> {
    boolean existsByEventId(UUID eventId);
}
