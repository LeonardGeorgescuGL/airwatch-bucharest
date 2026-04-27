package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ADMINISTRATOR_SISTEM")
public class AdministratorSistem extends User {
    @Column(name = "nivelacces", nullable = false)
    private String nivelAcces;

    public AdministratorSistem() {}
    public String getNivelAcces() { return nivelAcces; }
    public void setNivelAcces(String nivelAcces) { this.nivelAcces = nivelAcces; }
}
