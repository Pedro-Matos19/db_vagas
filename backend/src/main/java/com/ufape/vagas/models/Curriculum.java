package com.ufape.vagas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Curriculo") 
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curriculo")
    private Long id;

    @Column(name = "objetivo", columnDefinition = "LONGTEXT")
    private String objective;

    @Column(name = "experiencia", columnDefinition = "MEDIUMTEXT")
    private String experience;

    @Column(name = "link_portifolio", length = 1000)
    private String portfolioLink;

    @OneToOne
    @JoinColumn(name = "id_estudante", referencedColumnName = "id_estudante", nullable = false, unique = true)
    private Candidate candidate;

    public Curriculum() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getPortfolioLink() { return portfolioLink; }
    public void setPortfolioLink(String portfolioLink) { this.portfolioLink = portfolioLink; }

    public Candidate getCandidate() { return candidate; }
    public void setCandidate(Candidate candidate) { this.candidate = candidate; }
}