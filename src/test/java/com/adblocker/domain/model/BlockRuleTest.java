package com.adblocker.domain.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlockRuleTest {

    @Test
    void shouldHaveCorrectRuleTypes() {
        int expectedCount = 4;
        RuleType[] actualTypes = RuleType.values();
        assertEquals(expectedCount, actualTypes.length);
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        String emptyName = "";
        String validPattern = "test-pattern";
        RuleType validType = RuleType.ADVERTISEMENT;

        assertThrows(IllegalArgumentException.class, () -> new BlockRule(emptyName, validPattern, validType, 5, null));
    }

    @Test
    void shouldThrowExceptionWhenPatternIsEmpty() {
        String validName = "Regla válida";
        String emptyPattern = "";
        RuleType validType = RuleType.ADVERTISEMENT;

        assertThrows(IllegalArgumentException.class, () -> new BlockRule(validName, emptyPattern, validType, 5, null));
    }

    @Test
    void shouldThrowExceptionWhenPriorityIsLessThanOne() {
        String validName = "Regla válida";
        String validPattern = "test-pattern";
        RuleType validType = RuleType.ADVERTISEMENT;
        int invalidPriority = 0;

        assertThrows(IllegalArgumentException.class, () -> new BlockRule(validName, validPattern, validType, invalidPriority, null));
    }

    @Test
    void shouldThrowExceptionWhenPriorityIsGreaterThanTen() {
        String validName = "Regla válida";
        String validPattern = "test-pattern";
        RuleType validType = RuleType.ADVERTISEMENT;
        int invalidPriority = 11;

        assertThrows(IllegalArgumentException.class, () -> new BlockRule(validName, validPattern, validType, invalidPriority, null));
    }

    @Test
    void shouldThrowExceptionWhenTrackerPriorityIsGreaterThanFive() {
        String validName = "Bloquear trackers";
        String validPattern = "tracker-pattern";
        RuleType trackerType = RuleType.TRACKER;
        int invalidPriorityForTracker = 6;

        assertThrows(IllegalArgumentException.class, () -> new BlockRule(validName, validPattern, trackerType, invalidPriorityForTracker, null));
    }

    @Test
    void shouldCreateValidBlockRuleSuccessfully() {
        // Arrange
        String name = "Bloquear anuncios";
        String pattern = "ad-pattern";
        RuleType type = RuleType.ADVERTISEMENT;
        int priority = 3;

        // Act
        BlockRule rule = new BlockRule(name, pattern, type, priority, null);

        // Assert
        assertEquals(name, rule.getName());
        assertEquals(pattern, rule.getPattern());
        assertEquals(type, rule.getRuleType());
        assertEquals(priority, rule.getPriority());
    }

    @Test
    void shouldDeactivateRuleWhenCallingDeactivate() {
        // Arrange: Crear regla activa por defecto
        BlockRule rule = new BlockRule("Regla activa", "pattern", RuleType.ADVERTISEMENT, 3, null);

        // Act: Desactivar la regla
        rule.deactivate();

        // Assert: Verificar que ahora está inactiva
        assertFalse(rule.isActive(), "La regla debería estar inactiva después de desactivarla");
    }

    @Test
    void shouldActivateRuleWhenCallingActivate() {
        // Arrange: Crear regla y desactivarla primero
        BlockRule rule = new BlockRule("Regla inactiva", "pattern", RuleType.ADVERTISEMENT, 3, null);
        rule.deactivate();

        // Act: Volver a activar (re-activate)
        rule.activate();

        // Assert: Verificar que está activa nuevamente
        assertTrue(rule.isActive(), "La regla debería estar activa después de activarla");
    }

    @Test
    void shouldThrowExceptionWhenDeactivatingLastActiveRule() {
        // Arrange
        BlockRuleRepository mockRepo = mock(BlockRuleRepository.class);

        BlockRule rule = new BlockRule("Única regla", "pattern", RuleType.ADVERTISEMENT, 1, mockRepo);
        rule.setUserId("user-123");

        when(mockRepo.findActiveRulesByUserId("user-123"))
                .thenReturn(List.of(rule));

        // Act & Assert: Esperamos excepción al intentar desactivar la última regla
        assertThrows(IllegalStateException.class, rule::deactivate);
    }
}