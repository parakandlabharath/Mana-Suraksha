package org.manasuraksha.core.sms;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.manasuraksha.core.analysis.ThreatAnalyzer;
import org.manasuraksha.core.model.AnalysisResult;
import org.manasuraksha.core.model.Finding;
import org.manasuraksha.core.model.RiskLevel;
import org.manasuraksha.core.model.VerificationStatus;

/**
 * Small educational SMS rules prototype.
 * Not a production detector; rules have not been calibrated on real data.
 */
public final class SmsAnalyzer implements ThreatAnalyzer<String> {

    @Override
    public AnalysisResult analyze(String input) {
        Objects.requireNonNull(input, "SMS input must not be null");
        if (input.isBlank()) {
            throw new IllegalArgumentException("SMS input must not be blank");
        }

        String message = input.toLowerCase(Locale.ROOT);
        List<Finding> findings = new ArrayList<>();

        if (containsAny(message, "share your otp", "send your otp",
                "tell me your otp", "share otp", "send otp")) {
            findings.add(new Finding("OTP_DISCLOSURE_REQUEST",
                    "The message appears to ask you to disclose an OTP.", 5));
        }

        if (containsAny(message, "account will be blocked",
                "account will be suspended", "act immediately",
                "within 10 minutes")) {
            findings.add(new Finding("URGENT_ACCOUNT_THREAT",
                    "The message uses urgent account-related pressure.", 3));
        }

        if (findings.isEmpty()) {
            return new AnalysisResult(
                    RiskLevel.UNKNOWN, null, VerificationStatus.UNKNOWN,
                    List.of(),
                    List.of("Verify unexpected requests using the organisation's official app or contact details."),
                    List.of("This prototype checks only a few patterns; no match does not mean the message is safe."));
        }

        int score = Math.min(findings.stream().mapToInt(Finding::severity).sum() * 15, 100);
        RiskLevel level = score >= 70 ? RiskLevel.HIGH : RiskLevel.SUSPICIOUS;

        return new AnalysisResult(
                level, score, VerificationStatus.UNVERIFIED, findings,
                List.of("Do not share OTPs, PINs or passwords.",
                        "Verify independently using the official app or website."),
                List.of("Prototype rules only; this is not proof that the sender is fraudulent."));
    }

    private boolean containsAny(String message, String... phrases) {
        for (String phrase : phrases) {
            if (message.contains(phrase)) {
                return true;
            }
        }
        return false;
    }
}
