package com.ufape.vagas.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Estudante")
public class Candidate {

    @Id
    @Column(name = "id_estudante")
    private Long id;

    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(name = "nome", nullable = false, length = 80)
    private String name;

    @Column(name = "data_nascimento")
    private java.time.LocalDate birthDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_estudante")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "Possui",
        joinColumns = @JoinColumn(name = "id_estudante"),
        inverseJoinColumns = @JoinColumn(name = "id_habilidade")
    )
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(
        name = "Estuda", 
        joinColumns = @JoinColumn(name = "id_estudante"),
        inverseJoinColumns = @JoinColumn(name = "id_curso")
    )
    private List<Course> courses;

    public Candidate() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public java.time.LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(java.time.LocalDate birthDate) { this.birthDate = birthDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
}