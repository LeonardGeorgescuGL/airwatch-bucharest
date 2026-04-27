package com.airwatch.repository;

import com.airwatch.model.Beneficiu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficiuRepository extends JpaRepository<Beneficiu, Integer> {
    Optional<Beneficiu> findByPozitieTop(Integer pozitieTop);
}
