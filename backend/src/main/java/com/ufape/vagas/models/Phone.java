package com.ufape.vagas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Telefone") 
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefone")
    private Long id;

    @Column(name = "numero", nullable = false, length = 13)
    private String number;

    @ManyToOne
    @JoinColumn(name = "id_estudante", nullable = false)
    private Candidate candidate;

    public Phone() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public Candidate getCandidate() { return candidate; }
    public void setCandidate(Candidate candidate) { this.candidate = candidate; }
}