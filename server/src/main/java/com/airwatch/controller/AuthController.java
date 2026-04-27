package com.airwatch.controller;

import com.airwatch.model.CivicUser;
import com.airwatch.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class AuthController {

    @Autowired
    private AuthService authService;

    // POST /api/auth/register - creeaza cont nou (MEMBRU_COMUNITAR + UTILIZATOR)
    @PostMapping("/register")
    public ResponseEntity<CivicUser> register(@RequestBody CivicUser user) {
        try {
            return ResponseEntity.ok(authService.register(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // POST /api/auth/login - autentificare, salveaza user in sesiune
    @PostMapping("/login")
    public ResponseEntity<CivicUser> login(@RequestBody LoginRequest login, HttpSession session) {
        CivicUser user = authService.login(login.getEmail(), login.getPassword());
        if (user != null) {
            session.setAttribute("userId", user.getId());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).build();
    }

    // POST /api/auth/logout - logout (folosit de butonul Iesire din frontend)
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logout realizat cu succes"));
    }

    // GET /api/auth/me - returneaza profilul utilizatorului curent din sesiune
    @GetMapping("/me")
    public ResponseEntity<CivicUser> getMe(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).build();
        CivicUser user = authService.getById(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // PUT /api/auth/{id} - actualizeaza profilul (nume, neighborhood)
    @PutMapping("/{id}")
    public ResponseEntity<CivicUser> update(@PathVariable String id, @RequestBody CivicUser updated) {
        CivicUser result = authService.update(id, updated);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    // DELETE /api/auth/{id} - sterge contul unui utilizator
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        authService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

class LoginRequest {
    private String email;
    private String password;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
