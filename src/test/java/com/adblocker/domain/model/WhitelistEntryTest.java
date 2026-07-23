package com.adblocker.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class WhitelistEntryTest {

    @Test
    void shouldThrowExceptionWhenExpiryDateIsInThePast() {
        // Arrange: Fecha de expiración ANTERIOR a la fecha de agregado
        String userId = "user-123";
        String domain = "example.com";
        LocalDateTime addedAt = LocalDateTime.now();
        LocalDateTime expiresAt = addedAt.minusDays(1);

        assertThrows(IllegalArgumentException.class, () -> new WhitelistEntry(userId, domain, addedAt, expiresAt));
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsEmpty() {
        // Arrange: UserId vacío
        String emptyUserId = "";
        String validDomain = "example.com";
        LocalDateTime addedAt = LocalDateTime.now();
        LocalDateTime expiresAt = addedAt.plusDays(30);

        assertThrows(IllegalArgumentException.class, () -> new WhitelistEntry(emptyUserId, validDomain, addedAt, expiresAt));
    }

    @Test
    void shouldThrowExceptionWhenDomainIsEmpty() {
        // Arrange: Dominio vacío
        String validUserId = "user-123";
        String emptyDomain = "";
        LocalDateTime addedAt = LocalDateTime.now();
        LocalDateTime expiresAt = addedAt.plusDays(30);

        assertThrows(IllegalArgumentException.class, () -> new WhitelistEntry(validUserId, emptyDomain, addedAt, expiresAt));
    }

    @Test
    void shouldThrowExceptionWhenDomainHasInvalidFormat() {
        // Arrange: Dominio sin punto
        String validUserId = "user-123";
        String invalidDomain = "examplecom"; // Sin "."
        LocalDateTime addedAt = LocalDateTime.now();
        LocalDateTime expiresAt = addedAt.plusDays(30);

        assertThrows(IllegalArgumentException.class, () -> new WhitelistEntry(validUserId, invalidDomain, addedAt, expiresAt));
    }

    @Test
    void shouldCreateValidWhitelistEntrySuccessfully() {
        // Arrange: Datos válidos sin fecha de expiración
        String userId = "user-123";
        String domain = "example.com";
        LocalDateTime addedAt = LocalDateTime.now();

        // Act
        WhitelistEntry entry = new WhitelistEntry(userId, domain, addedAt, null);

        // Assert
        assertEquals(userId, entry.getUserId());
        assertEquals(domain, entry.getDomain());
        assertTrue(entry.isActive(), "La entrada debe estar activa por defecto");
        assertNull(entry.getExpiresAt(), "Sin fecha de expiración significa permanente");
    }

    @Test
    void shouldExpireEntryWhenCallingExpire() {
        // Arrange
        WhitelistEntry entry = new WhitelistEntry(
                "user-123", "example.com",
                LocalDateTime.now(), null
        );

        // Act
        entry.expire();

        // Assert
        assertFalse(entry.isActive(), "La entrada debería estar inactiva después de expirar");
    }

    @Test
    void shouldThrowExceptionWhenExpiringAlreadyExpiredEntry() {
        // Arrange: Entrada ya expirada
        WhitelistEntry entry = new WhitelistEntry(
                "user-123", "example.com",
                LocalDateTime.now(), null
        );
        entry.expire();

        // Act & Assert
        assertThrows(IllegalStateException.class, entry::expire);
    }
}