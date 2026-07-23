package com.adblocker.domain.model;

import java.time.LocalDateTime;

public class Advertisement {

    private final String url;
    private final String sourceDomain;
    private final AdType adType;
    private final DetectionMethod detectionMethod;
    private final String contentHash;
    private final LocalDateTime detectedAt;
    private boolean isBlocked;

    public Advertisement(String url, String sourceDomain, AdType adType,
                         DetectionMethod detectionMethod, String contentHash) {
        // Validación básica de URL (debe empezar con http:// o https://)
        if (url == null || !(url.startsWith("http://") || url.startsWith("https://"))) {
            throw new IllegalArgumentException("La URL debe comenzar con http:// o https://");
        }

        // Validación 2: SourceDomain no puede estar vacío
        if (sourceDomain == null || sourceDomain.trim().isEmpty()) {
            throw new IllegalArgumentException("El sourceDomain no puede estar vacío");
        }

        // Validación 3: ContentHash no puede ser nulo
        if (contentHash == null || contentHash.trim().isEmpty()) {
            throw new IllegalArgumentException("El contentHash no puede estar vacío");
        }

        this.url = url;
        this.sourceDomain = sourceDomain;
        this.adType = adType;
        this.detectionMethod = detectionMethod;
        this.contentHash = contentHash;
        this.detectedAt = LocalDateTime.now();
        this.isBlocked = false;
    }

    // Getters básicos
    public String getUrl() { return url; }
    public String getSourceDomain() { return sourceDomain; }
    public AdType getAdType() { return adType; }
    public DetectionMethod getDetectionMethod() { return detectionMethod; }
    public String getContentHash() { return contentHash; }
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public boolean isBlocked() { return isBlocked; }

    public void block() {
        if (this.isBlocked) {
            throw new IllegalStateException("No se puede bloquear un anuncio que ya está bloqueado");
        }
        this.isBlocked = true;
    }
}