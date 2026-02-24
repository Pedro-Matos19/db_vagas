package com.ufape.vagas.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Estudante") 
public class Candidate {

    @Id
    @Column(name = "id_estudante")
    private Long id;

    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @Column(name = "data_nascimento")
    private LocalDate birthDate;

    @OneToOne
    @MapsId 
    @JoinColumn(name = "id_estudante")
    private User user;

    public Candidate() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}