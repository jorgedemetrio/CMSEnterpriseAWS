package com.newsflow.forum.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "forum_posts")
@Getter
@Setter
public class ForumPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private ForumTopicEntity topic;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "status_dado", nullable = false)
    private Integer statusDado = 1;

    @Column(name = "datahora_criado")
    private LocalDateTime datahoraCriado = LocalDateTime.now();

}