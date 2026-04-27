package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "VIZITATOR")
public class Vizitator extends User {
    @Column(name = "nrvizite", columnDefinition = "INT DEFAULT 0")
    private Integer nrVizite = 0;

    public Vizitator() {}
    public Integer getNrVizite() { return nrVizite; }
    public void setNrVizite(Integer nrVizite) { this.nrVizite = nrVizite; }
}
