package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GEO_JSON")
public class GeoJson extends RaportCivic {
    @Column(name = "geodata", columnDefinition = "jsonb")
    private String geoData;

    public GeoJson() {}
    public String getGeoData() { return geoData; }
    public void setGeoData(String geoData) { this.geoData = geoData; }
}
