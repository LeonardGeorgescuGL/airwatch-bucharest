package com.airwatch.controller;

import com.airwatch.model.Beneficiu;
import com.airwatch.model.CivicUser;
import com.airwatch.repository.BeneficiuRepository;
import com.airwatch.repository.CivicUserRepository;
import com.airwatch.service.BeneficiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/beneficii")
@CrossOrigin(origins = "*")
public class BeneficiuController {

    @Autowired
    private BeneficiuService beneficiuService;

    @Autowired
    private BeneficiuRepository beneficiuRepo;

    @Autowired
    private CivicUserRepository civicUserRepo;

    // GET /api/beneficii - toate beneficiile definite
    @GetMapping
    public List<Beneficiu> getAll() {
        return beneficiuService.getAll();
    }

    // GET /api/beneficii/pozitie/{p} - beneficiul pentru pozitia p in top (1,2,3)
    @GetMapping("/pozitie/{p}")
    public ResponseEntity<Beneficiu> getByPozitie(@PathVariable Integer p) {
        return beneficiuService.getByPozitie(p)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/beneficii/user/{userId}
     * Returneaza beneficiul userului bazat pe pozitia sa CURENTA in leaderboard.
     * Aceasta este legatura dintre User si Beneficiu prin pozitia in clasament.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getBeneficiuUser(@PathVariable String userId) {
        // Ia top 3 din leaderboard
        List<CivicUser> top = civicUserRepo.findTopMembers(PageRequest.of(0, 3));

        int pozitie = -1;
        for (int i = 0; i < top.size(); i++) {
            if (top.get(i).getId().equals(userId)) {
                pozitie = i + 1; // pozitie 1-based
                break;
            }
        }

        if (pozitie == -1) {
            // Userul nu e in top 3 - niciun beneficiu
            Map<String, Object> resp = new HashMap<>();
            resp.put("inTop3", false);
            resp.put("pozitie", null);
            resp.put("beneficiu", null);
            return ResponseEntity.ok(resp);
        }

        Optional<Beneficiu> ben = beneficiuRepo.findByPozitieTop(pozitie);
        Map<String, Object> resp = new HashMap<>();
        resp.put("inTop3", true);
        resp.put("pozitie", pozitie);
        resp.put("beneficiu", ben.orElse(null));
        return ResponseEntity.ok(resp);
    }

    // POST /api/beneficii - creeaza beneficiu nou
    @PostMapping
    public Beneficiu create(@RequestBody Beneficiu b) {
        return beneficiuService.save(b);
    }

    // PUT /api/beneficii/{id} - actualizeaza beneficiu
    @PutMapping("/{id}")
    public ResponseEntity<Beneficiu> update(@PathVariable Integer id, @RequestBody Beneficiu b) {
        return ResponseEntity.ok(beneficiuService.update(id, b));
    }

    // DELETE /api/beneficii/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        beneficiuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
