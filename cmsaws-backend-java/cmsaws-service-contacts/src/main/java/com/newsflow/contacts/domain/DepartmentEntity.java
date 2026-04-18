package com.newsflow.contacts.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "status_dado", nullable = false)
    private Integer statusDado = 1;

    @Column(name = "status_conteudo", nullable = false)
    private Integer statusConteudo = 1;

    @Column(name = "datahora_publicacao")
    private LocalDateTime datahoraPublicacao;

    @Column(name = "datahora_despublicar")
    private LocalDateTime datahoraDespublicar;

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}