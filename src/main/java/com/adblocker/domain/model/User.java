package com.adblocker.domain.model;

public class User {

    private final String username;
    private final String email;
    private final String passwordHash;
    private boolean isActive;

    public User(String username, String email, String passwordHash) {
        //  Validación 1: Email válido
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("El email debe tener un formato válido");
        }

        //  Validación 2: Username entre 3 y 20 caracteres
        if (username == null || username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("El username debe tener entre 3 y 20 caracteres");
        }

        //  Validación 3: PasswordHash obligatorio
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("El passwordHash no puede estar vacío");
        }

        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isActive = true; // Usuario activo por defecto
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
}