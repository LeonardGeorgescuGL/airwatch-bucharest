package com.airwatch.repository;

import com.airwatch.model.Rang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RangRepository extends JpaRepository<Rang, Integer> {
}
