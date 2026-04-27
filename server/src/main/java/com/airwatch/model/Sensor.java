package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SENZOR")
public class Sensor {
    @Id
    private String id;
    
    private double lat;
    private double lng;
    
    @Transient
    private int aqi;
    
    @Transient
    private String category;
    
    @Column(name = "categorie")
    private Integer dbCategorie = 0;
    
    private double pm25;
    private double pm10;
    private double no2;
    private double o3;
    private double co;
    private double so2;
    // PostgreSQL lowercases: dataSource->datasource, healthRiskZone->healthriskzone, riskCluster->riskcluster
    @Column(name = "datasource")
    private String dataSource;
    private Integer cluster;
    @Column(name = "healthriskzone")
    private String healthRiskZone;
    @Column(name = "riskcluster")
    private Integer riskCluster;
    
    @ManyToOne
    @JoinColumn(name = "id_zona")
    private UrbanArea urbanArea;

    public Sensor() {}

    public Sensor(String id, double lat, double lng, int aqi, String category, int pm25, int pm10, int no2, int o3, double co, int so2, String dataSource) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.aqi = aqi;
        this.category = category;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.no2 = no2;
        this.o3 = o3;
        this.co = co;
        this.so2 = so2;
        this.dataSource = dataSource;

    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }

    public int getAqi() { return aqi; }
    public void setAqi(int aqi) { this.aqi = aqi; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPm25() { return pm25; }
    public void setPm25(double pm25) { this.pm25 = pm25; }

    public double getPm10() { return pm10; }
    public void setPm10(double pm10) { this.pm10 = pm10; }

    public double getNo2() { return no2; }
    public void setNo2(double no2) { this.no2 = no2; }

    public double getO3() { return o3; }
    public void setO3(double o3) { this.o3 = o3; }

    public double getCo() { return co; }
    public void setCo(double co) { this.co = co; }

    public double getSo2() { return so2; }
    public void setSo2(double so2) { this.so2 = so2; }

    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }

    public Integer getCluster() { return cluster; }
    public void setCluster(Integer cluster) { this.cluster = cluster; }

    public String getHealthRiskZone() { return healthRiskZone; }
    public void setHealthRiskZone(String healthRiskZone) { this.healthRiskZone = healthRiskZone; }

    public Integer getRiskCluster() { return riskCluster; }
    public void setRiskCluster(Integer riskCluster) { this.riskCluster = riskCluster; }
    public UrbanArea getUrbanArea() { return urbanArea; }
    public void setUrbanArea(UrbanArea urbanArea) { this.urbanArea = urbanArea; }
    
    public Integer getDbCategorie() { return dbCategorie; }
    public void setDbCategorie(Integer dbCategorie) { this.dbCategorie = dbCategorie; }
}
