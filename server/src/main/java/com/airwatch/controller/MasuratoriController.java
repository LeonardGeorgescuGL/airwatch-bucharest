package com.airwatch.controller;

import com.airwatch.model.Masuratori;
import com.airwatch.service.MasuratoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/masuratori")
@CrossOrigin(origins = "*")
public class MasuratoriController {

    @Autowired
    private MasuratoriService masuratoriService;

    // GET /api/masuratori - toate masuratorile (utile pentru ML/K-Means)
    @GetMapping
    public List<Masuratori> getAll() {
        return masuratoriService.getAll();
    }

    // GET /api/masuratori/senzor/{idSenzor} - ultimele 24 masuratori per senzor (FB Prophet)
    @GetMapping("/senzor/{idSenzor}")
    public List<Masuratori> getBySenzor(@PathVariable String idSenzor) {
        return masuratoriService.getUltimeleMasuratori(idSenzor);
    }

    // GET /api/masuratori/zona/{idZona} - masuratorile dintr-o zona ultimele 24h
    @GetMapping("/zona/{idZona}")
    public List<Masuratori> getByZona(@PathVariable Integer idZona) {
        return masuratoriService.getMasuratoriZonaUltimeleOre(idZona, 24);
    }

    // GET /api/masuratori/zona/{idZona}/zile/{n} - masuratorile dintr-o zona ultimele N*24 ore (pentru grafic istoric)
    @GetMapping("/zona/{idZona}/zile/{n}")
    public List<Masuratori> getByZonaZile(@PathVariable Integer idZona, @PathVariable Integer n) {
        return masuratoriService.getMasuratoriZonaUltimeleOre(idZona, n * 24);
    }

    // POST /api/masuratori - adauga masurare manuala
    @PostMapping
    public Masuratori create(@RequestBody Masuratori m) {
        return masuratoriService.saveMasurare(m);
    }

    // DELETE /api/masuratori/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        masuratoriService.deleteMasurare(id);
        return ResponseEntity.noContent().build();
    }
}
