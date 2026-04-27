package com.airwatch.service;

import com.airwatch.model.Beneficiu;
import com.airwatch.repository.BeneficiuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiuService {

    @Autowired
    private BeneficiuRepository beneficiuRepo;

    public List<Beneficiu> getAll() {
        return beneficiuRepo.findAll();
    }

    public Optional<Beneficiu> getByPozitie(Integer pozitie) {
        return beneficiuRepo.findByPozitieTop(pozitie);
    }

    public Beneficiu save(Beneficiu b) {
        return beneficiuRepo.save(b);
    }

    public Beneficiu update(Integer id, Beneficiu b) {
        b.setIdBeneficiu(id);
        return beneficiuRepo.save(b);
    }

    public void delete(Integer id) {
        beneficiuRepo.deleteById(id);
    }
}
