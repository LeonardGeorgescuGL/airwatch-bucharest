package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GEO_FOTO")
public class GeoFoto extends RaportCivic {
    private Double lat;
    private Double lng;
    private String fotografie;

    public GeoFoto() {}
    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }
    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }
    public String getFotografie() { return fotografie; }
    public void setFotografie(String fotografie) { this.fotografie = fotografie; }
}
