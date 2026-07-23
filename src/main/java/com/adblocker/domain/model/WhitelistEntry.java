package com.adblocker.domain.model;

import java.time.LocalDateTime;

public class WhitelistEntry {

    private final String userId;
    private final String domain;
    private final LocalDateTime addedAt;
    private final LocalDateTime expiresAt;
    private boolean isActive;

    public WhitelistEntry(String userId, String domain,
                          LocalDateTime addedAt, LocalDateTime expiresAt) {

        // Validación 1: UserId obligatorio
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("El userId no puede estar vacío");
        }

        // Validación 2: Dominio obligatorio
        if (domain == null || domain.trim().isEmpty()) {
            throw new IllegalArgumentException("El dominio no puede estar vacío");
        }

        // Validación 3: Formato de dominio válido (debe contener ".")
        if (!domain.contains(".")) {
            throw new IllegalArgumentException("El dominio debe tener un formato válido (ej: example.com)");
        }

        // Validación Crítica: Expiración debe ser futura
        if (expiresAt != null && !expiresAt.isAfter(addedAt)) {
            throw new IllegalArgumentException(
                    "La fecha de expiración debe ser posterior a la fecha de agregado"
            );
        }

        this.userId = userId;
        this.domain = domain;
        this.addedAt = addedAt;
        this.expiresAt = expiresAt;
        this.isActive = true; // Por defecto activa
    }

    // Getters básicos
    public String getUserId() { return userId; }
    public String getDomain() { return domain; }
    public LocalDateTime getAddedAt() { return addedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isActive() { return isActive; }

    public void expire() {
        if (!this.isActive) {
            throw new IllegalStateException("No se puede expirar una entrada que ya está inactiva");
        }
        this.isActive = false;
    }

}