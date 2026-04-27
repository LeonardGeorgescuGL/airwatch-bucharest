package com.airwatch.repository;

import com.airwatch.model.CivicUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CivicUserRepository extends JpaRepository<CivicUser, String> {
    
    // Leaderboard - top membri dupa puncte (paginat)
    @Query("SELECT m FROM CivicUser m ORDER BY m.points DESC")
    List<CivicUser> findTopMembers(Pageable pageable);
    
    CivicUser findByEmail(String email);
}
