package com.adblocker.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class BlockLogTest {

    @Test
    void shouldThrowExceptionWhenAutoBlockHasNoRuleId() {
        // Arrange: Bloqueo AUTO sin ruleId
        UUID userId = UUID.randomUUID();
        UUID blockedItemId = UUID.randomUUID();
        BlockType autoType = BlockType.AUTO;
        UUID nullRuleId = null;
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> {
            new BlockLog(userId, blockedItemId, autoType, nullRuleId, now);
        });
    }

    @Test
    void shouldThrowExceptionWhenManualBlockHasRuleId() {
        // Arrange: Bloqueo MANUAL con ruleId
        UUID userId = UUID.randomUUID();
        UUID blockedItemId = UUID.randomUUID();
        BlockType manualType = BlockType.MANUAL;
        UUID invalidRuleId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> {
            new BlockLog(userId, blockedItemId, manualType, invalidRuleId, now);
        });
    }
    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        // Arrange: UserId nulo
        UUID nullUserId = null;
        UUID blockedItemId = UUID.randomUUID();
        BlockType type = BlockType.MANUAL;
        UUID ruleId = null;
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> {
            new BlockLog(nullUserId, blockedItemId, type, ruleId, now);
        });
    }

    @Test
    void shouldThrowExceptionWhenBlockedItemIdIsNull() {
        // Arrange: BlockedItemId nulo
        UUID userId = UUID.randomUUID();
        UUID nullBlockedItemId = null;
        BlockType type = BlockType.MANUAL;
        UUID ruleId = null;
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> {
            new BlockLog(userId, nullBlockedItemId, type, ruleId, now);
        });
    }

    @Test
    void shouldThrowExceptionWhenBlockedAtIsInFuture() {
        // Arrange: Fecha futura
        UUID userId = UUID.randomUUID();
        UUID blockedItemId = UUID.randomUUID();
        BlockType type = BlockType.MANUAL;
        UUID ruleId = null;
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1); // Inválido

        assertThrows(IllegalArgumentException.class, () -> {
            new BlockLog(userId, blockedItemId, type, ruleId, futureDate);
        });
    }

}