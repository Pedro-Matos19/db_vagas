package com.ufape.vagas.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vaga") 
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vaga")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 50)
    private String title;

    @Column(name = "descricao", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "tipo", length = 50)
    private String type;

    @Column(name = "bolsa_salario", precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "status", length = 30)
    private String status = "Aberta"; 

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Company company;
    
    @ManyToMany
	@JoinTable(name = "requer",
	joinColumns = @JoinColumn(name = "id_vaga"),
	inverseJoinColumns = @JoinColumn(name = "id_habilidade"))
    private List<Skill> skills;

    public Job() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}