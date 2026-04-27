package com.airwatch.repository;

import com.airwatch.model.Masuratori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MasuratoriRepository extends JpaRepository<Masuratori, Integer> {
    List<Masuratori> findTop24BySensorIdOrderByTimestampDesc(String sensorId);
    
    // Dezlegam masuratorile de senzor (le pastram pentru ML/Prophet!)
    // Nu le stergem - sunt date istorice pretioase pentru K-Means si FB Prophet
    @Modifying
    @Query("UPDATE Masuratori m SET m.sensor = null WHERE m.sensor.id = :sensorId")
    void orphanBySensorId(@Param("sensorId") String sensorId);

    @Query("SELECT m FROM Masuratori m WHERE m.sensor.urbanArea.id = :idZona AND m.timestamp >= :de_la ORDER BY m.timestamp DESC")
    List<Masuratori> findByZonaAndTimestamp(@Param("idZona") Integer idZona, @Param("de_la") LocalDateTime de_la);
}