package com.newsflow.forum.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "forum_topics")
@Getter
@Setter
public class ForumTopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "status_dado", nullable = false)
    private Integer statusDado = 1;

    @Column(name = "datahora_criado")
    private LocalDateTime datahoraCriado = LocalDateTime.now();

}