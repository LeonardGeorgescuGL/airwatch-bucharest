package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "RANG")
public class Rang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rang")
    private Integer idRang;
    
    @Column(name = "tiprang", nullable = false)
    private String tipRang;
    
    @Column(nullable = false)
    private Integer nivel;
    
    @Column(name = "punctenecesare", columnDefinition = "INT DEFAULT 0")
    private Integer puncteNecesare = 0;

    public Rang() {}
    public Integer getIdRang() { return idRang; }
    public void setIdRang(Integer idRang) { this.idRang = idRang; }
    public String getTipRang() { return tipRang; }
    public void setTipRang(String tipRang) { this.tipRang = tipRang; }
    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }
    public Integer getPuncteNecesare() { return puncteNecesare; }
    public void setPuncteNecesare(Integer puncteNecesare) { this.puncteNecesare = puncteNecesare; }
}
