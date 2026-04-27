package com.airwatch.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MASURATORI")
public class Masuratori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_masurare")
    private Integer idMasurare;

    @ManyToOne
    @JoinColumn(name = "id_senzor")
    private Sensor sensor;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime timestamp;

    private Double pm25;
    private Double pm10;
    private Double no2;
    private Double o3;
    private Double so2;
    private Double co;
    private Integer aqi;

    public Masuratori() {}

    public Integer getIdMasurare() { return idMasurare; }
    public void setIdMasurare(Integer idMasurare) { this.idMasurare = idMasurare; }
    
    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Double getPm25() { return pm25; }
    public void setPm25(Double pm25) { this.pm25 = pm25; }

    public Double getPm10() { return pm10; }
    public void setPm10(Double pm10) { this.pm10 = pm10; }

    public Double getNo2() { return no2; }
    public void setNo2(Double no2) { this.no2 = no2; }

    public Double getO3() { return o3; }
    public void setO3(Double o3) { this.o3 = o3; }

    public Double getSo2() { return so2; }
    public void setSo2(Double so2) { this.so2 = so2; }

    public Double getCo() { return co; }
    public void setCo(Double co) { this.co = co; }

    public Integer getAqi() { return aqi; }
    public void setAqi(Integer aqi) { this.aqi = aqi; }
}
