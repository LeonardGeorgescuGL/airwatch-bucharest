package com.airwatch.controller;

import com.airwatch.model.UrbanArea;
import com.airwatch.service.UrbanAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urban-areas")
@CrossOrigin(origins = "*")
public class UrbanAreaController {

    @Autowired
    private UrbanAreaService areaService;

    // GET /api/urban-areas
    @GetMapping
    public List<UrbanArea> getAll() {
        return areaService.getAllUrbanAreas();
    }

    // GET /api/urban-areas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UrbanArea> getById(@PathVariable Integer id) {
        UrbanArea ua = areaService.getUrbanAreaById(id);
        return ua != null ? ResponseEntity.ok(ua) : ResponseEntity.notFound().build();
    }

    // POST /api/urban-areas
    @PostMapping
    public UrbanArea create(@RequestBody UrbanArea area) {
        return areaService.saveUrbanArea(area);
    }

    // PUT /api/urban-areas/{id} - actualizeaza zona urbana
    @PutMapping("/{id}")
    public ResponseEntity<UrbanArea> update(@PathVariable Integer id, @RequestBody UrbanArea area) {
        UrbanArea updated = areaService.updateUrbanArea(id, area);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // DELETE /api/urban-areas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        areaService.deleteUrbanArea(id);
        return ResponseEntity.noContent().build();
    }
}
