package com.newsflow.contacts.repository;

import com.newsflow.contacts.domain.ContactMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactMessageRepository extends JpaRepository<ContactMessageEntity, UUID> {
}