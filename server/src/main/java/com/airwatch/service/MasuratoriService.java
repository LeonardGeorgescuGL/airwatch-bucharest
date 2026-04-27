package com.airwatch.service;

import com.airwatch.model.Masuratori;
import com.airwatch.repository.MasuratoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MasuratoriService {

    @Autowired
    private MasuratoriRepository masuratoriRepository;

    // Toate masuratorile - pentru K-Means si FB Prophet
    public List<Masuratori> getAll() {
        return masuratoriRepository.findAll();
    }

    // Ultimele 24 masuratori per senzor - pentru grafice si Prophet
    public List<Masuratori> getUltimeleMasuratori(String idSenzor) {
        return masuratoriRepository.findTop24BySensorIdOrderByTimestampDesc(idSenzor);
    }

    // Masuratorile dintr-o zona pe o perioada - pentru harta de caldura
    public List<Masuratori> getMasuratoriZonaUltimeleOre(Integer idZona, int ore) {
        LocalDateTime deLa = LocalDateTime.now().minusHours(ore);
        return masuratoriRepository.findByZonaAndTimestamp(idZona, deLa);
    }

    public Masuratori saveMasurare(Masuratori m) {
        if (m.getTimestamp() == null) {
            m.setTimestamp(LocalDateTime.now());
        }
        return masuratoriRepository.save(m);
    }

    public void deleteMasurare(Integer id) {
        masuratoriRepository.deleteById(id);
    }
}
