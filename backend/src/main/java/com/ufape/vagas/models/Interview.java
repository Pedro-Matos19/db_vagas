package com.ufape.vagas.models;

import java.time.LocalDateTime;

import com.ufape.vagas.enums.InterviewStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Entrevista")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrevista")
    private Long id;

    @Column(name = "data_hora")
    private LocalDateTime dateTime;

    @Column(name = "link_local", length = 1000)
    private String location;

    @Column(name = "feedback", length = 1000)
    private String feedback;

    @Column(name = "status", length = 30)
    @Enumerated(value = EnumType.STRING)
    private InterviewStatus status;

    @ManyToOne
    @JoinColumn(name = "id_candidatura", nullable = false)
    private Application application;

    public Interview() {}

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
	public InterviewStatus getStatus() {
		return status;
	}


	public void setStatus(InterviewStatus status) {
		this.status = status;
	}


	public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
}