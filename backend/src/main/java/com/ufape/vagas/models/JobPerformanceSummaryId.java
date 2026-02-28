package com.ufape.vagas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class JobPerformanceSummaryId {

    @Column(name = "empresa", length = 80)
    private String companyName;

    @Column(name = "vaga", length = 50)
    private String jobTitle;

    @Column(name = "status_vaga", length = 30)
    private String jobStatus;

    @Column(name = "bolsa_salario", precision = 10, scale = 2)
    private BigDecimal salary;

    public JobPerformanceSummaryId() {
    }

    public JobPerformanceSummaryId(String companyName, String jobTitle, String jobStatus, BigDecimal salary) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobStatus = jobStatus;
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobPerformanceSummaryId that = (JobPerformanceSummaryId) o;
        return Objects.equals(companyName, that.companyName)
                && Objects.equals(jobTitle, that.jobTitle)
                && Objects.equals(jobStatus, that.jobStatus)
                && Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, jobTitle, jobStatus, salary);
    }
}
