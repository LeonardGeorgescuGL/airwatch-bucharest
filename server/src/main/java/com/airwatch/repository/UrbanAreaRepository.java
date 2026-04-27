package com.airwatch.repository;

import com.airwatch.model.UrbanArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrbanAreaRepository extends JpaRepository<UrbanArea, Integer> {
}
