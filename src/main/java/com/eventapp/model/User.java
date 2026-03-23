package com.eventapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrations;

    public User() {}

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return isActive; }
    public List<Registration> getRegistrations() { return registrations; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRole(Role role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String name, email, password, phone;
        private Role role = Role.USER;
        private boolean isActive = true;

        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder role(Role role) { this.role = role; return this; }
        public Builder isActive(boolean isActive) { this.isActive = isActive; return this; }

        public User build() {
            User u = new User();
            u.name = this.name;
            u.email = this.email;
            u.password = this.password;
            u.phone = this.phone;
            u.role = this.role;
            u.isActive = this.isActive;
            return u;
        }
    }

    public enum Role { USER, ADMIN, SUPER_ADMIN }
}