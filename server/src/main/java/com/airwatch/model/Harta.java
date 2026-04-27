package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "HARTA")
public class Harta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_harta")
    private Integer idHarta;

    @Column(name = "nrzoneurbane", columnDefinition = "INT DEFAULT 0")
    private Integer nrZoneUrbane = 0;

    @ManyToOne
    @JoinColumn(name = "id_vizitator")
    private Vizitator vizitator;

    @ManyToOne
    @JoinColumn(name = "id_membru")
    private CivicUser membru;

    @OneToOne
    @JoinColumn(name = "id_admin")
    private AdministratorSistem admin;

    public Harta() {}
    public Integer getIdHarta() { return idHarta; }
    public void setIdHarta(Integer idHarta) { this.idHarta = idHarta; }
    public Integer getNrZoneUrbane() { return nrZoneUrbane; }
    public void setNrZoneUrbane(Integer nrZoneUrbane) { this.nrZoneUrbane = nrZoneUrbane; }
    public Vizitator getVizitator() { return vizitator; }
    public void setVizitator(Vizitator vizitator) { this.vizitator = vizitator; }
    public CivicUser getMembru() { return membru; }
    public void setMembru(CivicUser membru) { this.membru = membru; }
    public AdministratorSistem getAdmin() { return admin; }
    public void setAdmin(AdministratorSistem admin) { this.admin = admin; }
}
