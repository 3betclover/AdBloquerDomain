package com.adblocker.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class BlockLog {

    private final UUID id;
    private final UUID userId;
    private final UUID blockedItemId;
    private final BlockType blockType;
    private final UUID ruleId;
    private final LocalDateTime blockedAt;
    private boolean wasSuccessful;

    public BlockLog(UUID userId, UUID blockedItemId, BlockType blockType,
                    UUID ruleId, LocalDateTime blockedAt) {

        this.id = UUID.randomUUID();

        // Validación 1: UserId obligatorio
        if (userId == null) {
            throw new IllegalArgumentException("El userId no puede ser nulo");
        }

        // Validación 2: BlockedItemId obligatorio
        if (blockedItemId == null) {
            throw new IllegalArgumentException("El blockedItemId no puede ser nulo");
        }

        // Validación 3: Fecha no puede ser futura
        if (blockedAt != null && blockedAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de bloqueo no puede ser futura");
        }

        // Validación Condicional 1: AUTO requiere ruleId
        if (blockType == BlockType.AUTO && ruleId == null) {
            throw new IllegalArgumentException(
                    "Los bloqueos automáticos deben tener una regla asociada"
            );
        }

        // Validación Condicional 2: MANUAL prohíbe ruleId
        if (blockType == BlockType.MANUAL && ruleId != null) {
            throw new IllegalArgumentException(
                    "Los bloqueos manuales no pueden tener una regla asociada"
            );
        }

        this.userId = userId;
        this.blockedItemId = blockedItemId;
        this.blockType = blockType;
        this.ruleId = ruleId;
        this.blockedAt = blockedAt;
        this.wasSuccessful = true; // Por defecto exitoso
    }

    // Getters básicos
    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getBlockedItemId() { return blockedItemId; }
    public BlockType getBlockType() { return blockType; }
    public UUID getRuleId() { return ruleId; }
    public LocalDateTime getBlockedAt() { return blockedAt; }
    public boolean wasSuccessful() { return wasSuccessful; }
}