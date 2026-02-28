package com.ufape.vagas.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Table(name = "v_detalhes_candidaturas")
@Immutable
public class DetailsApplication {

    @Id
    @Column(name = "id_candidatura")
    private Long idCandidatura;

    @Column(name = "nome_candidato", length = 100)
    private String nomeCandidato;

    @Column(name = "titulo_vaga", length = 50)
    private String tituloVaga;

    @Column(name = "nome_empresa", length = 80)
    private String nomeEmpresa;

    @Column(name = "data_aplicacao")
    private LocalDateTime dataAplicacao;

    @Column(name = "status_candidatura", length = 30)
    private String statusCandidatura;

    public DetailsApplication() {
    }

    public Long getIdCandidatura() {
        return idCandidatura;
    }

    public void setIdCandidatura(Long idCandidatura) {
        this.idCandidatura = idCandidatura;
    }

    public String getNomeCandidato() {
        return nomeCandidato;
    }

    public void setNomeCandidato(String nomeCandidato) {
        this.nomeCandidato = nomeCandidato;
    }

    public String getTituloVaga() {
        return tituloVaga;
    }

    public void setTituloVaga(String tituloVaga) {
        this.tituloVaga = tituloVaga;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public LocalDateTime getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDateTime dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public String getStatusCandidatura() {
        return statusCandidatura;
    }

    public void setStatusCandidatura(String statusCandidatura) {
        this.statusCandidatura = statusCandidatura;
    }
}
