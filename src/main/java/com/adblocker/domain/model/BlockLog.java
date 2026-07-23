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