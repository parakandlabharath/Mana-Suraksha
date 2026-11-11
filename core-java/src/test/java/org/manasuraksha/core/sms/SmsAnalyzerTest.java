package org.manasuraksha.core.sms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.manasuraksha.core.model.RiskLevel;

class SmsAnalyzerTest {
    private final SmsAnalyzer analyzer = new SmsAnalyzer();

    @Test
    void flagsExplicitOtpDisclosureRequest() {
        var result = analyzer.analyze("Please share your OTP immediately.");
        assertEquals(RiskLevel.HIGH, result.riskLevel());
        assertTrue(result.findings().stream()
                .anyMatch(f -> f.ruleCode().equals("OTP_DISCLOSURE_REQUEST")));
    }

    @Test
    void returnsUnknownWhenNoPrototypeRuleMatches() {
        var result = analyzer.analyze("Your appointment is confirmed for tomorrow.");
        assertEquals(RiskLevel.UNKNOWN, result.riskLevel());
        assertEquals(null, result.score());
    }

    @Test
    void rejectsBlankInput() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.analyze("  "));
    }

    @Test
    void rejectsNullInput() {
        assertThrows(NullPointerException.class, () -> analyzer.analyze(null));
    }
}
