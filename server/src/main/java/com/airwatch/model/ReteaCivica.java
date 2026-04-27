package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "RETEA_CIVICA")
public class ReteaCivica extends SursaDate {
    @Column(name = "nrcontributori", columnDefinition = "INT DEFAULT 0")
    private Integer nrContributori = 0;

    public ReteaCivica() {}
    public Integer getNrContributori() { return nrContributori; }
    public void setNrContributori(Integer nrContributori) { this.nrContributori = nrContributori; }
}
