package com.ufape.vagas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Candidatura")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidatura")
    private Long id;

    @Column(name = "status_atual", length = 30)
    private String status = "Em análise";

    @ManyToOne
    @JoinColumn(name = "id_estudante", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Job job;

    public Application() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Candidate getCandidate() { return candidate; }
    public void setCandidate(Candidate candidate) { this.candidate = candidate; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
}