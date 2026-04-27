package com.airwatch.controller;

import com.airwatch.model.Rang;
import com.airwatch.service.RangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rang")
@CrossOrigin(origins = "*")
public class RangController {

    @Autowired
    private RangService rangService;

    // GET /api/rang - toate rangurile
    @GetMapping
    public List<Rang> getAll() {
        return rangService.getAll();
    }

    // GET /api/rang/{id} - rang dupa ID
    @GetMapping("/{id}")
    public ResponseEntity<Rang> getById(@PathVariable Integer id) {
        Rang r = rangService.getById(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    // POST /api/rang - creeaza rang nou
    @PostMapping
    public Rang create(@RequestBody Rang rang) {
        return rangService.save(rang);
    }

    // PUT /api/rang/{id} - actualizeaza rang existent
    @PutMapping("/{id}")
    public ResponseEntity<Rang> update(@PathVariable Integer id, @RequestBody Rang rang) {
        Rang updated = rangService.update(id, rang);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // DELETE /api/rang/{id} - sterge rang
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        rangService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
