package com.airwatch.controller;

import com.airwatch.model.Sensor;
import com.airwatch.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin(origins = "*")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // GET /api/sensors
    @GetMapping
    public List<Sensor> getAll() {
        return sensorService.getAllSensors();
    }

    // GET /api/sensors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getById(@PathVariable String id) {
        Sensor s = sensorService.getSensorById(id);
        return s != null ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

    // GET /api/sensors/zona/{idZona}
    @GetMapping("/zona/{idZona}")
    public List<Sensor> getByZona(@PathVariable Integer idZona) {
        return sensorService.getSensorsByUrbanArea(idZona);
    }

    // POST /api/sensors
    @PostMapping
    public Sensor create(@RequestBody Sensor sensor) {
        return sensorService.saveSensor(sensor);
    }

    // PUT /api/sensors/{id} - actualizeaza valorile unui senzor existent
    @PutMapping("/{id}")
    public ResponseEntity<Sensor> update(@PathVariable String id, @RequestBody Sensor sensor) {
        sensor.setId(id);
        return ResponseEntity.ok(sensorService.saveSensor(sensor));
    }

    // DELETE /api/sensors/{id} - masuratorile raman cu sensor=null (pentru ML)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}
