package com.eventapp.dto;

public class AuthResponse {

    private String token;
    private String type;
    private Long id;
    private String name;
    private String email;
    private String role;

    public AuthResponse() {}

    // Getters
    public String getToken() { return token; }
    public String getType() { return type; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    // Setters
    public void setToken(String token) { this.token = token; }
    public void setType(String type) { this.type = type; }
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String token, type, name, email, role;
        private Long id;

        public Builder token(String token) { this.token = token; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder role(String role) { this.role = role; return this; }

        public AuthResponse build() {
            AuthResponse r = new AuthResponse();
            r.token = this.token;
            r.type = this.type;
            r.id = this.id;
            r.name = this.name;
            r.email = this.email;
            r.role = this.role;
            return r;
        }
    }
}