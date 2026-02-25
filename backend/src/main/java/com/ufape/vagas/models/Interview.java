package com.ufape.vagas.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Entrevista")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrevista")
    private Long id;

    @Column(name = "data_hora")
    private LocalDateTime dateTime;

    @Column(name = "link_local", length = 100)
    private String location;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "status", length = 30)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_candidatura", nullable = false)
    private Application application;

    public Interview() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
}