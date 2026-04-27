package com.airwatch.controller;

import com.airwatch.model.RaportCivic;
import com.airwatch.service.RaportCivicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rapoarte")
@CrossOrigin(origins = "*")
public class RaportCivicController {

    @Autowired
    private RaportCivicService raportService;

    // GET /api/rapoarte - toate rapoartele
    @GetMapping
    public List<RaportCivic> getAll() {
        return raportService.getAll();
    }

    // GET /api/rapoarte/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RaportCivic> getById(@PathVariable Integer id) {
        RaportCivic r = raportService.getById(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    // GET /api/rapoarte/zona/{idZona} - rapoartele dintr-o zona
    @GetMapping("/zona/{idZona}")
    public List<RaportCivic> getByZona(@PathVariable Integer idZona) {
        return raportService.getByZona(idZona);
    }

    // GET /api/rapoarte/tip/{tip} - ex: /api/rapoarte/tip/GEO_JSON
    @GetMapping("/tip/{tip}")
    public List<RaportCivic> getByTip(@PathVariable String tip) {
        return raportService.getByTip(tip);
    }

    // POST /api/rapoarte - creeaza raport civic nou
    @PostMapping
    public RaportCivic create(@RequestBody RaportCivic raport) {
        return raportService.save(raport);
    }

    // PUT /api/rapoarte/{id} - actualizeaza raport
    @PutMapping("/{id}")
    public ResponseEntity<RaportCivic> update(@PathVariable Integer id, @RequestBody RaportCivic raport) {
        RaportCivic updated = raportService.update(id, raport);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // DELETE /api/rapoarte/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        raportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
