package com.newsflow.forum.repository;

import com.newsflow.forum.domain.ForumPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ForumPostRepository extends JpaRepository<ForumPostEntity, UUID> {
}