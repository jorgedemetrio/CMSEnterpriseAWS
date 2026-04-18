package com.newsflow.forum.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "forum_posts")
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

    public UUID getId() { return id; }
    public ForumTopicEntity getTopic() { return topic; }
    public void setTopic(ForumTopicEntity topic) { this.topic = topic; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}