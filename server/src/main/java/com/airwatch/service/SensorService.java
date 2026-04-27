package com.airwatch.service;

import com.airwatch.model.Sensor;
import com.airwatch.repository.MasuratoriRepository;
import com.airwatch.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MasuratoriRepository masuratoriRepository;

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public Sensor getSensorById(String id) {
        Optional<Sensor> s = sensorRepository.findById(id);
        return s.orElse(null);
    }
    
    public List<Sensor> getSensorsByUrbanArea(Integer areaId) {
        return sensorRepository.findByUrbanArea_Id(areaId);
    }

    public Sensor saveSensor(Sensor sensor) {
        if(sensor.getId() == null || sensor.getId().isEmpty()) {
            sensor.setId(UUID.randomUUID().toString());
        }
        return sensorRepository.save(sensor);
    }
    
    @Transactional
    public void deleteSensor(String id) {
        // Dezlegam masuratorile (sensor -> null) ca sa pastram datele istorice
        // pentru K-Means clustering si FB Prophet predictions!
        masuratoriRepository.orphanBySensorId(id);
        sensorRepository.deleteById(id);
    }
}
