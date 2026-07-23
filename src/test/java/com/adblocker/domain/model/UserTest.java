package com.adblocker.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        // Arrange: Email sin "@" ni dominio válido
        String invalidEmail = "usuario-sin-arroba";
        String validUsername = "testuser";
        String validPasswordHash = "hashed-password-123";

        // Act & Assert: Esperamos excepción por email inválido
        assertThrows(IllegalArgumentException.class, () -> new User(validUsername, invalidEmail, validPasswordHash));
    }

    @Test
    void shouldThrowExceptionWhenUsernameIsTooShort() {
        // Arrange: Username de solo 2 caracteres
        String shortUsername = "ab";
        String validEmail = "test@example.com";
        String validPasswordHash = "hashed-123";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new User(shortUsername, validEmail, validPasswordHash));
    }

    @Test
    void shouldThrowExceptionWhenUsernameIsTooLong() {
        // Arrange: Username de 21 caracteres
        String longUsername = "abcdefghijklmnopqrstu";
        String validEmail = "test@example.com";
        String validPasswordHash = "hashed-123";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new User(longUsername, validEmail, validPasswordHash));
    }

    @Test
    void shouldThrowExceptionWhenPasswordHashIsEmpty() {
        // Arrange: Password hash vacío
        String validUsername = "testuser";
        String validEmail = "test@example.com";
        String emptyPasswordHash = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new User(validUsername, validEmail, emptyPasswordHash));
    }

    @Test
    void shouldCreateValidUserSuccessfully() {
        // Arrange: Datos válidos
        String username = "testuser";
        String email = "test@example.com";
        String passwordHash = "hashed-password-123";

        // Act: Crear usuario
        User user = new User(username, email, passwordHash);

        // Assert: Verificar que todos los datos se guardaron correctamente
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    void shouldDeactivateUserWhenCallingDeactivate() {
        // Arrange: Crear usuario activo por defecto
        User user = new User("testuser", "test@example.com", "hash-123");

        // Act: Desactivar usuario
        user.deactivate();

        // Assert: Verificar estado
        assertFalse(user.isActive(), "El usuario debería estar inactivo después de desactivarlo");
    }

    @Test
    void shouldActivateUserWhenCallingActivate() {
        // Arrange: Crear usuario y desactivarlo primero
        User user = new User("testuser", "test@example.com", "hash-123");
        user.deactivate();

        // Act: Volver a activar
        user.activate();

        // Assert: Verificar estado
        assertTrue(user.isActive(), "El usuario debería estar activo después de activarlo");
    }
}