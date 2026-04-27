package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ZONA_URBANA")
public class UrbanArea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zona")
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    private double lat;
    
    private double lng;
    
    // sensorsCount -> sensorscount in PostgreSQL
    @Column(name = "sensorscount", columnDefinition = "INT DEFAULT 0")
    private int sensorsCount;

    public UrbanArea() {}

    public UrbanArea(String name, double lat, double lng, int sensorsCount) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.sensorsCount = sensorsCount;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }

    public int getSensorsCount() { return sensorsCount; }
    public void setSensorsCount(int sensorsCount) { this.sensorsCount = sensorsCount; }
}
