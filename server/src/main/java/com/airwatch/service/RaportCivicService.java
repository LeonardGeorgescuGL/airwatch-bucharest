package com.airwatch.service;

import com.airwatch.model.RaportCivic;
import com.airwatch.repository.RaportCivicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.airwatch.model.CivicUser;
import com.airwatch.model.ValidareRaport;
import com.airwatch.repository.CivicUserRepository;
import com.airwatch.repository.ValidareRaportRepository;

@Service
public class RaportCivicService {

    @Autowired
    private RaportCivicRepository raportRepo;

    @Autowired
    private ValidareRaportRepository validareRepo;

    @Autowired
    private CivicUserRepository membruRepo;

    public List<RaportCivic> getAll() {
        return raportRepo.findAll();
    }

    public RaportCivic getById(Integer id) {
        return raportRepo.findById(id).orElse(null);
    }

    public List<RaportCivic> getByZona(Integer idZona) {
        return raportRepo.findByZonaUrbana_Id(idZona);
    }

    public List<RaportCivic> getByTip(String tip) {
        return raportRepo.findByTip(tip);
    }

    public RaportCivic save(RaportCivic r) {
        if (r.getDataEmitere() == null) {
            r.setDataEmitere(LocalDateTime.now());
        }

        // Instantiem corect subclasa bazat pe 'tip' pentru a se salva in tabelele copil (ex. RAPORT_TEXT)
        RaportCivic entityToSave = r;
        
        if ("TEXT".equalsIgnoreCase(r.getTip())) {
            com.airwatch.model.RaportText rt = new com.airwatch.model.RaportText();
            rt.setTitlu(r.getTitlu());
            rt.setTip(r.getTip());
            rt.setContinut(r.getContinut());
            rt.setDataEmitere(r.getDataEmitere());
            rt.setMembruId(r.getMembruId());
            rt.setZonaUrbana(r.getZonaUrbana());
            entityToSave = rt;
        } 
        // Aici se pot adauga in viitor si alte tipuri: FOTO, GEO_JSON daca se creeaza entitatile
        // else if ("FOTO".equalsIgnoreCase(r.getTip())) { ... }

        // Daca am primit un membruId de la frontend, cream legatura ValidareRaport
        if (entityToSave.getMembruId() != null) {
            CivicUser membru = membruRepo.findById(entityToSave.getMembruId()).orElse(null);
            if (membru != null) {
                ValidareRaport validare = new ValidareRaport();
                validare.setMembru(membru);
                validare.setDataValidarii(java.time.LocalDate.now());
                validare.setStatusRaport(false); // implicit nevalidat
                validare = validareRepo.save(validare);
                
                entityToSave.setValidare(validare);
            }
        }

        return raportRepo.save(entityToSave);
    }

    public RaportCivic update(Integer id, RaportCivic updated) {
        RaportCivic existing = raportRepo.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setTitlu(updated.getTitlu());
        existing.setTip(updated.getTip());
        existing.setContinut(updated.getContinut());
        return raportRepo.save(existing);
    }

    public void delete(Integer id) {
        raportRepo.deleteById(id);
    }
}
