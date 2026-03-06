package com.ufape.vagas.models;

import java.math.BigDecimal;
import java.util.List;

import com.ufape.vagas.enums.JobStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(value = EnumType.STRING)
    private JobStatus status;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Company company;

    @ManyToMany
    @JoinTable(
        name = "Requer_Habilidade",
        joinColumns = @JoinColumn(name = "id_vaga"),
        inverseJoinColumns = @JoinColumn(name = "id_habilidade")
    )
    private List<Skill> requiredSkills;

    public Job() {}

    public Job(String title, String description, String type, BigDecimal salary, JobStatus status, Company company, List<Skill> requiredSkills) {
        super();
        this.title = title;
        this.description = description;
        this.type = type;
        this.salary = salary;
        this.status = status;
        this.company = company;
        this.requiredSkills = requiredSkills; 
    }

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
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
    public List<Skill> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<Skill> requiredSkills) { this.requiredSkills = requiredSkills; }

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}
    
}