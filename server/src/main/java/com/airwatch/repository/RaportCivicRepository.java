package com.airwatch.repository;

import com.airwatch.model.RaportCivic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaportCivicRepository extends JpaRepository<RaportCivic, Integer> {
    List<RaportCivic> findByZonaUrbana_Id(Integer idZona);
    List<RaportCivic> findByTip(String tip);
}
