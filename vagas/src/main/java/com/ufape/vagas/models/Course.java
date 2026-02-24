package com.ufape.vagas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Curso") 
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long id;

    @Column(name = "nome_curso", nullable = false, length = 80)
    private String name;

    @Column(name = "nivel", length = 50)
    private String level;

    public Course() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}