package org.manasuraksha.core.model;

import java.util.Objects;

/** One explainable indicator found during analysis. */
public record Finding(String ruleCode, String explanation, int severity) {
    public Finding {
        Objects.requireNonNull(ruleCode, "ruleCode must not be null");
        Objects.requireNonNull(explanation, "explanation must not be null");
        if (ruleCode.isBlank()) {
            throw new IllegalArgumentException("ruleCode must not be blank");
        }
        if (severity < 1 || severity > 5) {
            throw new IllegalArgumentException("severity must be between 1 and 5");
        }
    }
}
