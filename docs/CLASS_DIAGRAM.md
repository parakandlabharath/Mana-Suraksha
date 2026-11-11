# Domain Class Diagram

Proposed relationships; not all classes are implemented yet.

```mermaid
classDiagram
 class ThreatAnalyzer~T~ {
  <<interface>>
  +analyze(T input) AnalysisResult
 }
 class SmsAnalyzer
 class UrlAnalyzer
 class PhoneReputationAnalyzer
 class RiskAssessmentService
 class AnalysisResult
 class Finding
 class FraudReport
 class ReportModerationService
 ThreatAnalyzer <|.. SmsAnalyzer
 ThreatAnalyzer <|.. UrlAnalyzer
 ThreatAnalyzer <|.. PhoneReputationAnalyzer
 SmsAnalyzer --> RiskAssessmentService
 UrlAnalyzer --> RiskAssessmentService
 PhoneReputationAnalyzer --> RiskAssessmentService
 RiskAssessmentService --> AnalysisResult
 AnalysisResult *-- Finding
 ReportModerationService --> FraudReport
```

Use composition to combine analyzers and findings. Keep domain objects independent of Spring controllers and database entities.
