package com.airwatch.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "VALIDARE_RAPORT")
public class ValidareRaport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_validare")
    private Integer idValidare;

    @Column(name = "datavalidarii")
    private LocalDate dataValidarii;

    @Column(name = "statusraport", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean statusRaport = false;

    @Column(name = "motivrespingere")
    private String motivRespingere;

    @ManyToOne
    @JoinColumn(name = "id_admin")
    private AdministratorSistem admin;

    @ManyToOne
    @JoinColumn(name = "id_membru")
    private CivicUser membru;

    public ValidareRaport() {}
    public Integer getIdValidare() { return idValidare; }
    public void setIdValidare(Integer idValidare) { this.idValidare = idValidare; }
    public LocalDate getDataValidarii() { return dataValidarii; }
    public void setDataValidarii(LocalDate dataValidarii) { this.dataValidarii = dataValidarii; }
    public Boolean getStatusRaport() { return statusRaport; }
    public void setStatusRaport(Boolean statusRaport) { this.statusRaport = statusRaport; }
    public String getMotivRespingere() { return motivRespingere; }
    public void setMotivRespingere(String motivRespingere) { this.motivRespingere = motivRespingere; }
    public AdministratorSistem getAdmin() { return admin; }
    public void setAdmin(AdministratorSistem admin) { this.admin = admin; }
    public CivicUser getMembru() { return membru; }
    public void setMembru(CivicUser membru) { this.membru = membru; }
}
