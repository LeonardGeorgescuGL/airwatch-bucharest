package com.airwatch.controller;

import com.airwatch.model.CivicUser;
import com.airwatch.repository.CivicUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membri")
@CrossOrigin(origins = "*")
public class GamificationController {

    @Autowired
    private CivicUserRepository civicUserRepository;

    // GET /api/membri/leaderboard - top 10 membri dupa puncte (din DB real!)
    @GetMapping("/leaderboard")
    public List<CivicUser> getLeaderboard() {
        return civicUserRepository.findTopMembers(PageRequest.of(0, 10));
    }

    // GET /api/membri - toti membrii
    @GetMapping
    public List<CivicUser> getAll() {
        return civicUserRepository.findAll();
    }

    // GET /api/membri/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CivicUser> getById(@PathVariable String id) {
        CivicUser m = civicUserRepository.findById(id).orElse(null);
        return m != null ? ResponseEntity.ok(m) : ResponseEntity.notFound().build();
    }

    // PUT /api/membri/{id} - actualizeaza profilul unui membru (puncte, neighborhood)
    @PutMapping("/{id}")
    public ResponseEntity<CivicUser> update(@PathVariable String id, @RequestBody CivicUser updated) {
        CivicUser existing = civicUserRepository.findById(id).orElse(null);
        if (existing == null) return ResponseEntity.notFound().build();
        existing.setName(updated.getName());
        existing.setNeighborhood(updated.getNeighborhood());
        existing.setPoints(updated.getPoints());
        return ResponseEntity.ok(civicUserRepository.save(existing));
    }

    // DELETE /api/membri/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        civicUserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
