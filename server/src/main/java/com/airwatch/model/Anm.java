package com.airwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ANM")
public class Anm extends SursaDate {
    @Column(name = "modelmeteorologic")
    private String modelMeteorologic;

    public Anm() {}
    public String getModelMeteorologic() { return modelMeteorologic; }
    public void setModelMeteorologic(String modelMeteorologic) { this.modelMeteorologic = modelMeteorologic; }
}
