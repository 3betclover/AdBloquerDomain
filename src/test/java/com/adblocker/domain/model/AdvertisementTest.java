package com.adblocker.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdvertisementTest {

    @Test
    void shouldThrowExceptionWhenUrlIsInvalid() {
        // Arrange: URL sin protocolo http/https
        String invalidUrl = "www.ejemplo.com/anuncio";
        String validDomain = "ejemplo.com";
        AdType validType = AdType.BANNER;
        DetectionMethod validMethod = DetectionMethod.PATTERN;
        String validHash = "abc123hash";

        // Act & Assert: Esperamos excepción por URL inválida
        assertThrows(IllegalArgumentException.class, () -> new Advertisement(invalidUrl, validDomain, validType, validMethod, validHash));
    }

    @Test
    void shouldThrowExceptionWhenSourceDomainIsEmpty() {
        // Arrange: Dominio fuente vacío
        String validUrl = "https://example.com/ad";
        String emptyDomain = "";
        AdType validType = AdType.BANNER;
        DetectionMethod validMethod = DetectionMethod.PATTERN;
        String validHash = "abc123";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Advertisement(validUrl, emptyDomain, validType, validMethod, validHash));
    }

    @Test
    void shouldThrowExceptionWhenContentHashIsNull() {
        // Arrange: Hash nulo
        String validUrl = "https://example.com/ad";
        String validDomain = "example.com";
        AdType validType = AdType.BANNER;
        DetectionMethod validMethod = DetectionMethod.PATTERN;
        String nullHash = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Advertisement(validUrl, validDomain, validType, validMethod, nullHash));
    }

    @Test
    void shouldCreateValidAdvertisementSuccessfully() {
        // Arrange: Datos válidos
        String url = "https://example.com/ad-banner";
        String domain = "example.com";
        AdType type = AdType.BANNER;
        DetectionMethod method = DetectionMethod.PATTERN;
        String hash = "abc123hash";

        // Act: Crear anuncio
        Advertisement ad = new Advertisement(url, domain, type, method, hash);

        // Assert: Verificar datos y estado inicial
        assertEquals(url, ad.getUrl());
        assertEquals(domain, ad.getSourceDomain());
        assertEquals(type, ad.getAdType());
        assertEquals(method, ad.getDetectionMethod());
        assertEquals(hash, ad.getContentHash());
        assertFalse(ad.isBlocked(), "Un anuncio nuevo debe estar desbloqueado por defecto");
    }

    @Test
    void shouldBlockAdvertisementWhenCallingBlock() {
        // Arrange: Crear anuncio válido (desbloqueado por defecto)
        Advertisement ad = new Advertisement(
                "https://example.com/ad",
                "example.com",
                AdType.BANNER,
                DetectionMethod.PATTERN,
                "hash123"
        );

        // Act: Bloquear el anuncio
        ad.block();

        // Assert: Verificar que ahora está bloqueado
        assertTrue(ad.isBlocked(), "El anuncio debería estar bloqueado");
    }

    @Test
    void shouldThrowExceptionWhenBlockingAlreadyBlockedAd() {
        // Arrange: Crear anuncio y bloquearlo primero
        Advertisement ad = new Advertisement(
                "https://example.com/ad",
                "example.com",
                AdType.BANNER,
                DetectionMethod.PATTERN,
                "hash123"
        );
        ad.block();

        // Act & Assert: Intentar bloquear de nuevo debe lanzar excepción
        assertThrows(IllegalStateException.class, ad::block);
    }
}