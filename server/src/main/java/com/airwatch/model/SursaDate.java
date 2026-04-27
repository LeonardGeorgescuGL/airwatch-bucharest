package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SURSA_DATE")
@Inheritance(strategy = InheritanceType.JOINED)
public class SursaDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsursa")
    private Integer idSursa;

    @Column(nullable = false)
    private String tip;

    private String endpoint;
    private Double fiabilitate;

    public SursaDate() {}
    public Integer getIdSursa() { return idSursa; }
    public void setIdSursa(Integer idSursa) { this.idSursa = idSursa; }
    public String getTip() { return tip; }
    public void setTip(String tip) { this.tip = tip; }
    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public Double getFiabilitate() { return fiabilitate; }
    public void setFiabilitate(Double fiabilitate) { this.fiabilitate = fiabilitate; }
}
