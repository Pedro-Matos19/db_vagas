package com.ufape.vagas.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "v_resumo_desempenho_vagas")
@Immutable
public class JobPerformanceSummary {

    @EmbeddedId
    private JobPerformanceSummaryId id;

    @Column(name = "total_candidatos")
    private Long totalCandidates;

    public JobPerformanceSummary() {
    }

    public JobPerformanceSummary(JobPerformanceSummaryId id, Long totalCandidates) {
        this.id = id;
        this.totalCandidates = totalCandidates;
    }

    public JobPerformanceSummaryId getId() {
        return id;
    }

    public void setId(JobPerformanceSummaryId id) {
        this.id = id;
    }

    public Long getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(Long totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public String getCompanyName() {
        return id != null ? id.getCompanyName() : null;
    }

    public String getJobTitle() {
        return id != null ? id.getJobTitle() : null;
    }

    public String getJobStatus() {
        return id != null ? id.getJobStatus() : null;
    }

    public java.math.BigDecimal getSalary() {
        return id != null ? id.getSalary() : null;
    }
}
