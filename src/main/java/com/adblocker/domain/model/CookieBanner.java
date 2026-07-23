package com.adblocker.domain.model;

import java.time.LocalDateTime;

public class CookieBanner {

    private final String url;
    private final String domain;
    private final CookieBannerType bannerType;
    private final boolean hasAcceptButton;
    private final boolean hasRejectButton;
    private final String elementSelector;
    private final LocalDateTime detectedAt;
    private boolean isBlocked;

    public CookieBanner(String url, String domain, CookieBannerType bannerType,
                        boolean hasAcceptButton, boolean hasRejectButton, String elementSelector) {
        // Validación 1: URL con protocolo
        if (url == null || !(url.startsWith("http://") || url.startsWith("https://"))) {
            throw new IllegalArgumentException("La URL debe comenzar con http:// o https://");
        }

        //  Validación 2: Dominio obligatorio
        if (domain == null || domain.trim().isEmpty()) {
            throw new IllegalArgumentException("El dominio no puede estar vacío");
        }

        // Validación 3: Selector CSS obligatorio
        if (elementSelector == null || elementSelector.trim().isEmpty()) {
            throw new IllegalArgumentException("El selector CSS no puede estar vacío");
        }
        // Validación Legal: GDPR/CCPA requieren botón de rechazo
        if ((bannerType == CookieBannerType.GDPR || bannerType == CookieBannerType.CCPA)
                && !hasRejectButton) {
            throw new IllegalArgumentException(
                    "Los banners GDPR/CCPA deben tener obligatoriamente un botón de rechazo"
            );
        }

        this.url = url;
        this.domain = domain;
        this.bannerType = bannerType;
        this.hasAcceptButton = hasAcceptButton;
        this.hasRejectButton = hasRejectButton;
        this.elementSelector = elementSelector;
        this.detectedAt = LocalDateTime.now();
        this.isBlocked = false;
    }

    // Getters básicos
    public String getUrl() { return url; }
    public String getDomain() { return domain; }
    public CookieBannerType getBannerType() { return bannerType; }
    public boolean hasAcceptButton() { return hasAcceptButton; }
    public boolean hasRejectButton() { return hasRejectButton; }
    public String getElementSelector() { return elementSelector; }
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public boolean isBlocked() { return isBlocked; }

    public void block() {
        if (this.isBlocked) {
            throw new IllegalStateException("No se puede bloquear un banner que ya está bloqueado");
        }
        this.isBlocked = true;
    }
}