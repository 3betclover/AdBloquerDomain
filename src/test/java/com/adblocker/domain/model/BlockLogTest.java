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
}