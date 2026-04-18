package com.newsflow.forum.repository;

import com.newsflow.forum.domain.ForumTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ForumTopicRepository extends JpaRepository<ForumTopicEntity, UUID> {
}