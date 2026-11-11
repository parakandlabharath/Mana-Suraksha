package org.manasuraksha.core.url;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.manasuraksha.core.analysis.ThreatAnalyzer;
import org.manasuraksha.core.model.AnalysisResult;
import org.manasuraksha.core.model.Finding;
import org.manasuraksha.core.model.RiskLevel;
import org.manasuraksha.core.model.VerificationStatus;

/** Safe, local URL-structure checks. This class never opens or fetches the URL. */
public final class UrlAnalyzer implements ThreatAnalyzer<String> {
    @Override
    public AnalysisResult analyze(String input) {
        Objects.requireNonNull(input, "URL must not be null");
        if (input.isBlank() || input.length() > 2048) {
            throw new IllegalArgumentException("URL must contain 1 to 2048 characters");
        }

        final URI uri;
        try {
            uri = new URI(input.trim());
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("URL format is invalid", ex);
        }

        List<Finding> findings = new ArrayList<>();
        String scheme = uri.getScheme();
        String host = uri.getHost();

        if (scheme == null || !(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
            findings.add(new Finding("UNSAFE_OR_MISSING_SCHEME",
                    "The link does not use a recognised HTTP or HTTPS scheme.", 3));
        }
        if (host == null || host.isBlank()) {
            findings.add(new Finding("HOSTNAME_NOT_RECOGNISED",
                    "The hostname could not be safely identified.", 3));
        } else {
            String lowerHost = host.toLowerCase(Locale.ROOT);
            if (lowerHost.startsWith("xn--") || lowerHost.contains(".xn--")) {
                findings.add(new Finding("PUNYCODE_HOST",
                        "The hostname contains an internationalised punycode label; check it carefully.", 2));
            }
            if (lowerHost.matches("\\d{1,3}(\\.\\d{1,3}){3}")) {
                findings.add(new Finding("IP_LITERAL_HOST",
                        "The link uses an IP address instead of a familiar domain name.", 2));
            }
        }
        if (uri.getUserInfo() != null) {
            findings.add(new Finding("URL_USER_INFO",
                    "The URL contains user-info before the hostname, which can be misleading.", 4));
        }

        if (findings.isEmpty()) {
            return new AnalysisResult(RiskLevel.UNKNOWN, null, VerificationStatus.UNKNOWN,
                    List.of(),
                    List.of("Do not enter personal details unless you independently confirm the website."),
                    List.of("Only URL structure was checked; the destination was not opened or reputation-checked."));
        }

        int score = Math.min(findings.stream().mapToInt(Finding::severity).sum() * 15, 100);
        RiskLevel level = score >= 70 ? RiskLevel.HIGH : RiskLevel.SUSPICIOUS;
        return new AnalysisResult(level, score, VerificationStatus.UNVERIFIED, findings,
                List.of("Do not open the link if you were not expecting it.",
                        "Visit the organisation using its official app or a known address."),
                List.of("This prototype does not visit the URL or confirm that the destination is malicious."));
    }
}
