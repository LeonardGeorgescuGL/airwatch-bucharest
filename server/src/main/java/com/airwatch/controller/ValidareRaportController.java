package com.airwatch.controller;

import com.airwatch.model.ValidareRaport;
import com.airwatch.service.ValidareRaportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validare-rapoarte")
@CrossOrigin(origins = "*")
public class ValidareRaportController {

    @Autowired
    private ValidareRaportService validareService;

    // GET /api/validare-rapoarte - toate validarile
    @GetMapping
    public List<ValidareRaport> getAll() {
        return validareService.getAll();
    }

    // GET /api/validare-rapoarte/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ValidareRaport> getById(@PathVariable Integer id) {
        ValidareRaport v = validareService.getById(id);
        return v != null ? ResponseEntity.ok(v) : ResponseEntity.notFound().build();
    }

    // GET /api/validare-rapoarte/membru/{membruId} - validarile unui membru
    @GetMapping("/membru/{membruId}")
    public List<ValidareRaport> getByMembru(@PathVariable String membruId) {
        return validareService.getByMembru(membruId);
    }

    // GET /api/validare-rapoarte/admin/{adminId} - validarile unui admin
    @GetMapping("/admin/{adminId}")
    public List<ValidareRaport> getByAdmin(@PathVariable String adminId) {
        return validareService.getByAdmin(adminId);
    }

    // POST /api/validare-rapoarte - creeaza validare noua
    @PostMapping
    public ValidareRaport create(@RequestBody ValidareRaport v) {
        return validareService.save(v);
    }

    // PUT /api/validare-rapoarte/{id} - actualizeaza status validare
    @PutMapping("/{id}")
    public ResponseEntity<ValidareRaport> update(@PathVariable Integer id, @RequestBody ValidareRaport v) {
        ValidareRaport updated = validareService.update(id, v);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // DELETE /api/validare-rapoarte/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        validareService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
