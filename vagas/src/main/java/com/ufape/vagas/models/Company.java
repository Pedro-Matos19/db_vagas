package com.ufape.vagas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Empresa")
public class Company {

    @Id
    @Column(name = "id_empresa")
    private Long id;

    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;

    @Column(name = "razao_social", nullable = false, length = 80)
    private String corporateName;

    @Column(name = "localizacao", length = 50)
    private String location;

    @Column(name = "site", length = 1000)
    private String website;

    @Column(name = "setor_atuacao", length = 30)
    private String sector;

    @Column(name = "descricao", columnDefinition = "LONGTEXT")
    private String description;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_empresa")
    private User user;

    public Company() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getCorporateName() { return corporateName; }
    public void setCorporateName(String corporateName) { this.corporateName = corporateName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}