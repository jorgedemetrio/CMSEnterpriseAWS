package com.newsflow.core.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "articles")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "is_highlight", nullable = false)
    private boolean isHighlight = false;

    @Column(name = "status_dado", nullable = false)
    private Integer statusDado = 1;

    @Column(name = "status_conteudo", nullable = false)
    private Integer statusConteudo = 1;

    @Column(name = "datahora_publicacao")
    private LocalDateTime datahoraPublicacao;

    @Column(name = "datahora_despublicar")
    private LocalDateTime datahoraDespublicar;

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public CategoryEntity getCategory() { return category; }
    public void setCategory(CategoryEntity category) { this.category = category; }
    public boolean isHighlight() { return isHighlight; }
    public void setHighlight(boolean highlight) { isHighlight = highlight; }
    public Integer getStatusDado() { return statusDado; }
    public void setStatusDado(Integer statusDado) { this.statusDado = statusDado; }
}