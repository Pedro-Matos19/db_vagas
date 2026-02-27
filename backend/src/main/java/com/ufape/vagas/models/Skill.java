package com.ufape.vagas.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "Habilidade") 
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habilidade")
    private Long id;

    @Column(name = "nome", nullable = false, length = 30)
    private String name;

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private List<Candidate> candidates;

    @ManyToMany(mappedBy = "requiredSkills")
    @JsonIgnore 
    private List<Job> requiredInJobs;

    public Skill() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Candidate> getCandidates() { return candidates; }
    public void setCandidates(List<Candidate> candidates) { this.candidates = candidates; }

    public List<Job> getRequiredInJobs() { return requiredInJobs; }
    public void setRequiredInJobs(List<Job> requiredInJobs) { this.requiredInJobs = requiredInJobs; }
}