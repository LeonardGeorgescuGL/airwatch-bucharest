package com.airwatch.repository;

import com.airwatch.model.ValidareRaport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidareRaportRepository extends JpaRepository<ValidareRaport, Integer> {
    List<ValidareRaport> findByMembru_Id(String membruId);
    List<ValidareRaport> findByAdmin_Id(String adminId);
}
