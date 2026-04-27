package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UTILIZATOR")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    private String id;

    @Column(name = "nume", nullable = false)
    private String name; 
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "rol", nullable = false)
    private String role = "USER";
    
    // PostgreSQL lowercases unquoted identifiers: passwordHash -> passwordhash
    @Column(name = "passwordhash", nullable = false)
    private String passwordHash = "";

    public User() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
