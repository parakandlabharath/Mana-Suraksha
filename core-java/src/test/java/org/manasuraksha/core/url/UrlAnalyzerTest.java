package org.manasuraksha.core.url;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.manasuraksha.core.model.RiskLevel;

class UrlAnalyzerTest {
    private final UrlAnalyzer analyzer = new UrlAnalyzer();

    @Test
    void flagsIpLiteralHost() {
        var result = analyzer.analyze("http://192.0.2.10/login");
        assertEquals(RiskLevel.SUSPICIOUS, result.riskLevel());
        assertTrue(result.findings().stream().anyMatch(f -> f.ruleCode().equals("IP_LITERAL_HOST")));
    }

    @Test
    void unknownMeansNoPrototypeRuleMatchedNotGuaranteedSafe() {
        var result = analyzer.analyze("https://example.com");
        assertEquals(RiskLevel.UNKNOWN, result.riskLevel());
    }

    @Test
    void rejectsMalformedUrl() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.analyze("http://[bad"));
    }
}
