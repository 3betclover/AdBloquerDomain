package com.adblocker.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CookieBannerTest {

    @Test
    void shouldThrowExceptionWhenGdprBannerHasNoRejectButton() {
        // Arrange: Banner GDPR sin botón de rechazo
        String url = "https://example.com";
        String domain = "example.com";
        CookieBannerType gdprType = CookieBannerType.GDPR;
        boolean hasAccept = true;
        boolean hasReject = false;
        String selector = "#cookie-banner";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new CookieBanner(url, domain, gdprType, hasAccept, hasReject, selector));
    }

    @Test
    void shouldThrowExceptionWhenUrlIsInvalid() {
        // Arrange: URL sin protocolo
        String invalidUrl = "www.ejemplo.com";
        String validDomain = "ejemplo.com";
        CookieBannerType type = CookieBannerType.GENERAL;
        boolean hasAccept = true;
        boolean hasReject = true;
        String selector = "#banner";

        assertThrows(IllegalArgumentException.class, () -> new CookieBanner(invalidUrl, validDomain, type, hasAccept, hasReject, selector));
    }

    @Test
    void shouldThrowExceptionWhenDomainIsEmpty() {
        // Arrange: Dominio vacío
        String validUrl = "https://ejemplo.com";
        String emptyDomain = "";
        CookieBannerType type = CookieBannerType.GENERAL;
        boolean hasAccept = true;
        boolean hasReject = true;
        String selector = "#banner";

        assertThrows(IllegalArgumentException.class, () -> new CookieBanner(validUrl, emptyDomain, type, hasAccept, hasReject, selector));
    }

    @Test
    void shouldThrowExceptionWhenElementSelectorIsEmpty() {
        // Arrange: Selector CSS vacío
        String validUrl = "https://ejemplo.com";
        String validDomain = "ejemplo.com";
        CookieBannerType type = CookieBannerType.GENERAL;
        boolean hasAccept = true;
        boolean hasReject = true;
        String emptySelector = "";

        assertThrows(IllegalArgumentException.class, () -> new CookieBanner(validUrl, validDomain, type, hasAccept, hasReject, emptySelector));
    }

    @Test
    void shouldCreateValidCookieBannerSuccessfully() {
        // Arrange: Datos válidos
        String url = "https://example.com";
        String domain = "example.com";
        CookieBannerType type = CookieBannerType.GENERAL;
        boolean hasAccept = true;
        boolean hasReject = false;
        String selector = "#cookie-banner";

        // Act
        CookieBanner banner = new CookieBanner(url, domain, type, hasAccept, hasReject, selector);

        // Assert
        assertEquals(url, banner.getUrl());
        assertEquals(domain, banner.getDomain());
        assertEquals(type, banner.getBannerType());
        assertFalse(banner.isBlocked(), "Un banner nuevo debe estar desbloqueado por defecto");
    }

    @Test
    void shouldBlockBannerWhenCallingBlock() {
        // Arrange
        CookieBanner banner = new CookieBanner(
                "https://example.com", "example.com",
                CookieBannerType.GENERAL, true, false, "#banner"
        );

        // Act
        banner.block();

        // Assert
        assertTrue(banner.isBlocked(), "El banner debería estar bloqueado");
    }

    @Test
    void shouldThrowExceptionWhenBlockingAlreadyBlockedBanner() {
        // Arrange: Banner ya bloqueado
        CookieBanner banner = new CookieBanner(
                "https://example.com", "example.com",
                CookieBannerType.GENERAL, true, false, "#banner"
        );
        banner.block();

        // Act & Assert
        assertThrows(IllegalStateException.class, banner::block);
    }
}