package com.eventapp.controller;

import com.eventapp.model.User;
import com.eventapp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/super-admin")
public class AdminManagementController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminManagementController(UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Map<String, Object>>> getAllAdmins(
            Authentication auth) {
        if (!isSuperAdmin(auth)) return ResponseEntity.status(403).build();
        List<Map<String, Object>> admins = userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == User.Role.ADMIN
                        || u.getRole() == User.Role.SUPER_ADMIN)
                .map(u -> Map.<String, Object>of(
                        "id", u.getId(),
                        "name", u.getName(),
                        "email", u.getEmail(),
                        "role", u.getRole().name(),
                        "isActive", u.isActive()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(admins);
    }

    @PostMapping("/admins")
    public ResponseEntity<?> createAdmin(
            @RequestBody Map<String, String> body,
            Authentication auth) {
        if (!isSuperAdmin(auth)) return ResponseEntity.status(403).build();

        String email = body.get("email");
        String name = body.get("name");
        String password = body.get("password");
        String phone = body.getOrDefault("phone", "0000000000");

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email already exists"));
        }

        User admin = new User();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setPhone(phone);
        admin.setRole(User.Role.ADMIN);
        admin.setActive(true);
        userRepository.save(admin);

        return ResponseEntity.ok(
                Map.of("message", "Admin created successfully"));
    }

    @PutMapping("/promote/{userId}")
    public ResponseEntity<?> promoteToAdmin(
            @PathVariable Long userId,
            Authentication auth) {
        if (!isSuperAdmin(auth)) return ResponseEntity.status(403).build();
        return userRepository.findById(userId).map(user -> {
            user.setRole(User.Role.ADMIN);
            userRepository.save(user);
            return ResponseEntity.ok(
                    Map.of("message", "User promoted to Admin"));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/demote/{userId}")
    public ResponseEntity<?> demoteToUser(
            @PathVariable Long userId,
            Authentication auth) {
        if (!isSuperAdmin(auth)) return ResponseEntity.status(403).build();
        return userRepository.findById(userId).map(user -> {
            user.setRole(User.Role.USER);
            userRepository.save(user);
            return ResponseEntity.ok(
                    Map.of("message", "Admin demoted to User"));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/deactivate/{userId}")
    public ResponseEntity<?> deactivateUser(
            @PathVariable Long userId,
            Authentication auth) {
        if (!isSuperAdmin(auth)) return ResponseEntity.status(403).build();
        return userRepository.findById(userId).map(user -> {
            user.setActive(false);
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "User deactivated"));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers(
            Authentication auth) {
        if (!isSuperAdmin(auth)) return ResponseEntity.status(403).build();
        List<Map<String, Object>> users = userRepository.findAll()
                .stream()
                .map(u -> Map.<String, Object>of(
                        "id", u.getId(),
                        "name", u.getName(),
                        "email", u.getEmail(),
                        "role", u.getRole().name(),
                        "isActive", u.isActive()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    private boolean isSuperAdmin(Authentication auth) {
        if (auth == null) return false;
        return userRepository.findByEmail(auth.getName())
                .map(u -> u.getRole() == User.Role.SUPER_ADMIN)
                .orElse(false);
    }
}