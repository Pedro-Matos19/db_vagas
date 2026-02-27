package com.ufape.vagas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") 
    private Long id;

    @Column(name = "e_mail", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "senha", nullable = false, length = 100)
    private String password;

    public User() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}