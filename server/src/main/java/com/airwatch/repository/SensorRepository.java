package com.airwatch.repository;

import com.airwatch.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
    List<Sensor> findByUrbanArea_Id(Integer urbanAreaId);
}
