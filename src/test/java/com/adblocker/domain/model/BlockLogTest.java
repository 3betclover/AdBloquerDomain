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

        assertThrows(IllegalArgumentException.class, () -> new BlockLog(userId, blockedItemId, autoType, nullRuleId, now));
    }

    @Test
    void shouldThrowExceptionWhenManualBlockHasRuleId() {
        // Arrange: Bloqueo MANUAL con ruleId
        UUID userId = UUID.randomUUID();
        UUID blockedItemId = UUID.randomUUID();
        BlockType manualType = BlockType.MANUAL;
        UUID invalidRuleId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> new BlockLog(userId, blockedItemId, manualType, invalidRuleId, now));
    }
    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        // Arrange: UserId nulo
        UUID nullUserId = null;
        UUID blockedItemId = UUID.randomUUID();
        BlockType type = BlockType.MANUAL;
        UUID ruleId = null;
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> new BlockLog(nullUserId, blockedItemId, type, ruleId, now));
    }

    @Test
    void shouldThrowExceptionWhenBlockedItemIdIsNull() {
        // Arrange: BlockedItemId nulo
        UUID userId = UUID.randomUUID();
        UUID nullBlockedItemId = null;
        BlockType type = BlockType.MANUAL;
        UUID ruleId = null;
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> new BlockLog(userId, nullBlockedItemId, type, ruleId, now));
    }

    @Test
    void shouldThrowExceptionWhenBlockedAtIsInFuture() {
        // Arrange: Fecha futura
        UUID userId = UUID.randomUUID();
        UUID blockedItemId = UUID.randomUUID();
        BlockType type = BlockType.MANUAL;
        UUID ruleId = null;
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

        assertThrows(IllegalArgumentException.class, () -> new BlockLog(userId, blockedItemId, type, ruleId, futureDate));
    }

    @Test
    void shouldCreateValidAutoBlockLogSuccessfully() {
        // Arrange: Bloqueo AUTO válido con ruleId
        UUID userId = UUID.randomUUID();
        UUID blockedItemId = UUID.randomUUID();
        BlockType autoType = BlockType.AUTO;
        UUID ruleId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        // Act
        BlockLog log = new BlockLog(userId, blockedItemId, autoType, ruleId, now);

        // Assert
        assertEquals(userId, log.getUserId());
        assertEquals(blockedItemId, log.getBlockedItemId());
        assertEquals(autoType, log.getBlockType());
        assertEquals(ruleId, log.getRuleId());
        assertTrue(log.wasSuccessful(), "Un log nuevo debe ser exitoso por defecto");
    }

    @Test
    void shouldMarkLogAsFailedWhenCallingMarkAsFailed() {
        // Arrange
        BlockLog log = new BlockLog(
                UUID.randomUUID(), UUID.randomUUID(),
                BlockType.MANUAL, null, LocalDateTime.now()
        );

        // Act
        log.markAsFailed();

        // Assert
        assertFalse(log.wasSuccessful(), "El log debería estar marcado como fallido");
    }

    @Test
    void shouldThrowExceptionWhenMarkingAlreadyFailedLog() {
        // Arrange: Log ya fallido
        BlockLog log = new BlockLog(
                UUID.randomUUID(), UUID.randomUUID(),
                BlockType.MANUAL, null, LocalDateTime.now()
        );
        log.markAsFailed();

        // Act & Assert
        assertThrows(IllegalStateException.class, log::markAsFailed);
    }
}