package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "RAPORT_TEXT")
public class RaportText extends RaportCivic {
    @Column(name = "idzona")
    private Integer idZonaRef;
    @Column(name = "denumirezona")
    private String denumireZona;

    public RaportText() {}
    public Integer getIdZonaRef() { return idZonaRef; }
    public void setIdZonaRef(Integer idZonaRef) { this.idZonaRef = idZonaRef; }
    public String getDenumireZona() { return denumireZona; }
    public void setDenumireZona(String denumireZona) { this.denumireZona = denumireZona; }
}
