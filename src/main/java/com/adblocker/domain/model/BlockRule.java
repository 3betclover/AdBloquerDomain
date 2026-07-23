package com.adblocker.domain.model;

import java.util.List;

public class BlockRule {

    private final String name;
    private final String pattern;
    private final RuleType ruleType;
    private final int priority;
    private boolean isActive;
    private String userId;
    private final BlockRuleRepository repository;

    public BlockRule(String name, String pattern, RuleType ruleType, int priority, BlockRuleRepository repository) {
        // Validación 1: Nombre obligatorio
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la regla no puede estar vacío");
        }

        // Validación 2: Pattern obligatorio
        if (pattern == null || pattern.trim().isEmpty()) {
            throw new IllegalArgumentException("El patrón de la regla no puede estar vacío");
        }

        // Validación 3: Prioridad entre 1 y 10
        if (priority < 1 || priority > 10) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 10");
        }

        // Validación 4: TRACKER no puede tener prioridad > 5
        if (ruleType == RuleType.TRACKER && priority > 5) {
            throw new IllegalArgumentException(
                    "Las reglas de tipo TRACKER no pueden tener prioridad mayor a 5"
            );
        }

        // Asignación de atributos (solo si pasa todas las validaciones)
        this.name = name;
        this.pattern = pattern;
        this.ruleType = ruleType;
        this.priority = priority;
        this.isActive = true;
        this.repository = repository;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        if (userId != null && repository != null) {
            List<BlockRule> activeRules = repository.findActiveRulesByUserId(userId);
            if (activeRules.size() == 1) {
                throw new IllegalStateException(
                    "No se puede desactivar la última regla activa del usuario"
                );
            }
        }
        this.isActive = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    // Getters básicos para acceder a los datos desde los tests
    public String getName() { return name; }
    public String getPattern() { return pattern; }
    public RuleType getRuleType() { return ruleType; }
    public int getPriority() { return priority; }
}