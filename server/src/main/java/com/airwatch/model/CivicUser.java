package com.airwatch.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "MEMBRU_COMUNITAR")
@EqualsAndHashCode(callSuper = true)
public class CivicUser extends User {
    
    // points si neighborhood sunt deja lowercase in Postgres
    @Column(name = "points", columnDefinition = "INT DEFAULT 0")
    private int points = 0;
    
    @Column(name = "neighborhood")
    private String neighborhood;
    
    @Column(name = "confirmariraport", columnDefinition = "INT DEFAULT 0")
    private int confirmariRaport = 0;

    public CivicUser() {}

    public CivicUser(String id, String name, int points, String neighborhood) {
        super();
        this.setId(id);
        this.setName(name);
        this.points = points;
        this.neighborhood = neighborhood;
    }



    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getNeighborhood() { return neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }
}
