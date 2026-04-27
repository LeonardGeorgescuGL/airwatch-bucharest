package com.airwatch.service;

import com.airwatch.model.CivicUser;
import com.airwatch.repository.CivicUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private CivicUserRepository civicUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CREATE - inregistrare cont nou
    public CivicUser register(CivicUser user) {
        user.setId(UUID.randomUUID().toString());
        user.setRole("USER");
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setPoints(0);
        return civicUserRepository.save(user);
    }

    // READ - autentificare
    public CivicUser login(String email, String rawPassword) {
        CivicUser user = civicUserRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    // READ - get by ID (folosit de /me)
    public CivicUser getById(String id) {
        return civicUserRepository.findById(id).orElse(null);
    }

    // UPDATE - actualizeaza profilul
    public CivicUser update(String id, CivicUser updated) {
        CivicUser existing = civicUserRepository.findById(id).orElse(null);
        if (existing == null) return null;
        if (updated.getName() != null) existing.setName(updated.getName());
        if (updated.getNeighborhood() != null) existing.setNeighborhood(updated.getNeighborhood());
        // Nu actualiza parola direct prin update profil - foloseste un endpoint separat
        return civicUserRepository.save(existing);
    }

    // DELETE - sterge contul
    public void delete(String id) {
        civicUserRepository.deleteById(id);
    }
}
