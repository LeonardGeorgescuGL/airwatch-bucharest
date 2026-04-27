package com.airwatch.service;

import com.airwatch.model.Rang;
import com.airwatch.repository.RangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RangService {

    @Autowired
    private RangRepository rangRepository;

    public List<Rang> getAll() {
        return rangRepository.findAll();
    }

    public Rang getById(Integer id) {
        return rangRepository.findById(id).orElse(null);
    }

    public Rang save(Rang rang) {
        return rangRepository.save(rang);
    }

    public Rang update(Integer id, Rang updated) {
        Rang existing = rangRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setTipRang(updated.getTipRang());
        existing.setNivel(updated.getNivel());
        existing.setPuncteNecesare(updated.getPuncteNecesare());
        return rangRepository.save(existing);
    }

    public void delete(Integer id) {
        rangRepository.deleteById(id);
    }
}
