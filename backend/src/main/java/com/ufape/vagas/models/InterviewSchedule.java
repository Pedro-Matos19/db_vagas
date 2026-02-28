package com.ufape.vagas.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Table(name = "v_agenda_entrevistas")
@Immutable
public class InterviewSchedule {

    @Id
    @Column(name = "id_entrevista")
    private Long idInterview;

    @Column(name = "data_da_entrevista")
    private LocalDateTime interviewDateTime;

    @Column(name = "candidato", length = 100)
    private String candidateName;

    @Column(name = "vaga", length = 50)
    private String jobTitle;

    @Column(name = "empresa", length = 80)
    private String companyName;

    @Column(name = "link_reuniao", length = 1000)
    private String meetingLink;

    @Column(name = "status_entrevista", length = 30)
    private String interviewStatus;

    public InterviewSchedule() {
    }

    public Long getIdInterview() {
        return idInterview;
    }

    public void setIdInterview(Long idInterview) {
        this.idInterview = idInterview;
    }

    public LocalDateTime getInterviewDateTime() {
        return interviewDateTime;
    }

    public void setInterviewDateTime(LocalDateTime interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(String interviewStatus) {
        this.interviewStatus = interviewStatus;
    }
}
