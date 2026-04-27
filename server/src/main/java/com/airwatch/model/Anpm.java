package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ANPM")
public class Anpm extends SursaDate {
    @Column(name = "nivelcertificare")
    private String nivelCertificare;

    public Anpm() {}
    public String getNivelCertificare() { return nivelCertificare; }
    public void setNivelCertificare(String nivelCertificare) { this.nivelCertificare = nivelCertificare; }
}
