package com.ufape.vagas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Habilidade") 
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habilidade")
    private Long id;

    @Column(name = "nome", nullable = false, length = 30)
    private String name;

    public Skill() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}