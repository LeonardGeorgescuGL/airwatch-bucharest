package com.airwatch.service;

import com.airwatch.model.ValidareRaport;
import com.airwatch.repository.ValidareRaportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidareRaportService {

    @Autowired
    private ValidareRaportRepository validareRepo;

    public List<ValidareRaport> getAll() {
        return validareRepo.findAll();
    }

    public ValidareRaport getById(Integer id) {
        return validareRepo.findById(id).orElse(null);
    }

    public List<ValidareRaport> getByMembru(String membruId) {
        return validareRepo.findByMembru_Id(membruId);
    }

    public List<ValidareRaport> getByAdmin(String adminId) {
        return validareRepo.findByAdmin_Id(adminId);
    }

    public ValidareRaport save(ValidareRaport v) {
        return validareRepo.save(v);
    }

    public ValidareRaport update(Integer id, ValidareRaport updated) {
        ValidareRaport existing = validareRepo.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setStatusRaport(updated.getStatusRaport());
        existing.setMotivRespingere(updated.getMotivRespingere());
        existing.setDataValidarii(updated.getDataValidarii());
        return validareRepo.save(existing);
    }

    public void delete(Integer id) {
        validareRepo.deleteById(id);
    }
}
