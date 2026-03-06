package com.ufape.vagas.models;

import java.time.LocalDateTime;
import java.util.List;

import com.ufape.vagas.enums.ApplicationStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Candidatura")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidatura")
    private Long id;
    
    @Column(name = "status_atual")
    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "id_estudante", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Job job;

    @Column(name = "data_aplicacao")
    private LocalDateTime applicationDate = LocalDateTime.now();
 
    public Application() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }

    public Candidate getCandidate() { return candidate; }
    public void setCandidate(Candidate candidate) { this.candidate = candidate; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
}