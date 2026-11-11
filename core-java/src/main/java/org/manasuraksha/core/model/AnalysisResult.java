package org.manasuraksha.core.model;

import java.util.List;
import java.util.Objects;

/** Immutable analysis output. Score is a policy score, not a probability. */
public record AnalysisResult(
        RiskLevel riskLevel,
        Integer score,
        VerificationStatus verificationStatus,
        List<Finding> findings,
        List<String> recommendations,
        List<String> limitations) {

    public AnalysisResult {
        Objects.requireNonNull(riskLevel, "riskLevel must not be null");
        Objects.requireNonNull(verificationStatus, "verificationStatus must not be null");
        findings = List.copyOf(Objects.requireNonNull(findings, "findings must not be null"));
        recommendations = List.copyOf(Objects.requireNonNull(recommendations, "recommendations must not be null"));
        limitations = List.copyOf(Objects.requireNonNull(limitations, "limitations must not be null"));
        if (score != null && (score < 0 || score > 100)) {
            throw new IllegalArgumentException("score must be between 0 and 100");
        }
        if (riskLevel == RiskLevel.UNKNOWN && score != null) {
            throw new IllegalArgumentException("UNKNOWN results must not carry a numeric score");
        }
    }
}
