package com.ufape.vagas.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id_estudante")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "Possui_Habilidade",
        joinColumns = @JoinColumn(name = "id_estudante"),
        inverseJoinColumns = @JoinColumn(name = "id_habilidade")
    )
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(
        name = "Estuda_Curso",
        joinColumns = @JoinColumn(name = "id_estudante"),
        inverseJoinColumns = @JoinColumn(name = "id_curso")
    )
    private List<Course> courses;

    public Candidate() {}

    public Candidate(String cpf, String name, LocalDate birthDate, User user, List<Skill> skills, List<Course> courses) {
        super();
        this.cpf = cpf;
        this.name = name;
        this.birthDate = birthDate;
        this.user = user;
        this.skills = skills;
        this.courses = courses;
    }

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
    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }
    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
}