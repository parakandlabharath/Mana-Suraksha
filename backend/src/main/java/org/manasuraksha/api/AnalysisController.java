package org.manasuraksha.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.manasuraksha.core.analysis.ThreatAnalyzer;
import org.manasuraksha.core.model.AnalysisResult;
import org.manasuraksha.core.phone.PhoneNumberNormalizer;
import org.manasuraksha.core.sms.SmsAnalyzer;
import org.manasuraksha.core.url.UrlAnalyzer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@org.springframework.web.bind.annotation.CrossOrigin(origins = "http://localhost:5500")
@RequestMapping("/api/v1/analysis")
public class AnalysisController {
    private final ThreatAnalyzer<String> smsAnalyzer = new SmsAnalyzer();
    private final ThreatAnalyzer<String> urlAnalyzer = new UrlAnalyzer();

    public record TextRequest(@NotBlank @Size(max = 5000) String text) { }
    public record UrlRequest(@NotBlank @Size(max = 2048) String url) { }
    public record PhoneRequest(@NotBlank String phone, String countryCode) { }
    public record PhoneResult(String normalisedNumber, String reputationStatus, String message) { }

    @PostMapping("/sms")
    public AnalysisResult analyseSms(@Valid @RequestBody TextRequest request) {
        return smsAnalyzer.analyze(request.text());
    }

    @PostMapping("/url")
    public AnalysisResult analyseUrl(@Valid @RequestBody UrlRequest request) {
        return urlAnalyzer.analyze(request.url());
    }

    @PostMapping("/phone")
    public PhoneResult checkPhone(@Valid @RequestBody PhoneRequest request) {
        if (request.countryCode() != null && !request.countryCode().equals("+91")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only India (+91) is supported in this prototype");
        }
        String normalised = PhoneNumberNormalizer.normaliseIndia(request.phone());
        return new PhoneResult(normalised, "UNKNOWN",
                "No live caller reputation provider is connected. Caller ID can be spoofed.");
    }
}
