# Learning notes — Mana Suraksha

These notes describe concepts visible in the current source. They are not claims about planned database or Android work.

## 1. Interfaces and polymorphism
- **Meaning:** An interface defines a contract. Different classes can implement the same contract.
- **Used here:** `core-java/src/main/java/org/manasuraksha/core/analysis/ThreatAnalyzer.java`; implemented by `SmsAnalyzer.java` and `UrlAnalyzer.java`.
- **Tiny example:**
  ```java
  ThreatAnalyzer<String> analyzer = new SmsAnalyzer();
  AnalysisResult result = analyzer.analyze("Share your OTP");
  ```
- **Common mistake:** Treating an interface as if it contains the full implementation.

## 2. Generics
- **Meaning:** Generics let a type or interface work with a declared type parameter.
- **Used here:** `ThreatAnalyzer<T>`; the current SMS and URL implementations use `String`.
- **Tiny example:** `ThreatAnalyzer<String>`
- **Common mistake:** Using raw types and losing compile-time type checking.

## 3. Collections
- **Meaning:** Collections store groups of objects. A `List` preserves order and can grow.
- **Used here:** `SmsAnalyzer.java` and `UrlAnalyzer.java` build `List<Finding>` results.
- **Tiny example:** `List<Finding> findings = new ArrayList<>();`
- **Common mistake:** Assuming a list cannot be empty or modifying an immutable list.

## 4. Exception handling and input checks
- **Meaning:** Exceptions report invalid or unexpected input; validation rejects it before normal processing.
- **Used here:** `SmsAnalyzer.java`, `UrlAnalyzer.java`, `PhoneNumberNormalizer.java`, and `AnalysisController.java`.
- **Tiny example:** `if (input.isBlank()) throw new IllegalArgumentException("Input required");`
- **Common mistake:** Catching every exception and silently continuing.

## 5. Records
- **Meaning:** A Java record is a concise way to define a data carrier.
- **Used here:** Request and response records in `AnalysisController.java`.
- **Tiny example:** `public record TextRequest(String text) {}`
- **Common mistake:** Expecting a record component to have a setter; records are immutable data carriers.

## 6. Spring Boot REST controllers
- **Meaning:** A controller maps HTTP requests to Java methods.
- **Used here:** `backend/src/main/java/org/manasuraksha/api/AnalysisController.java` and `HealthController.java`.
- **Tiny example:** `@PostMapping("/sms")` maps a POST request to a method.
- **Common mistake:** Putting all domain rules inside the controller instead of calling a separate domain class.

## 7. Bean validation
- **Meaning:** Validation annotations check request data before controller logic runs.
- **Used here:** `AnalysisController.java` uses `@Valid`, `@NotBlank`, and `@Size`.
- **Tiny example:** `record TextRequest(@NotBlank @Size(max = 5000) String text) {}`
- **Common mistake:** Assuming annotations replace all domain-level validation.

## 8. URI parsing
- **Meaning:** A URI parser separates a link into parts such as scheme, host and user information.
- **Used here:** `core-java/.../url/UrlAnalyzer.java`.
- **Tiny example:** `URI uri = new URI(input.trim());`
- **Common mistake:** Assuming a syntactically valid URL is trustworthy. This code does not visit or verify the destination.

## 9. Unit testing
- **Meaning:** A unit test checks one small piece of logic with known input and expected output.
- **Used here:** `core-java/src/test/java/.../sms`, `url`, `phone`, and backend tests.
- **Tiny example:** Test that an invalid phone number throws an exception.
- **Common mistake:** Testing only happy paths and ignoring invalid or boundary inputs.

## 10. JSON REST communication
- **Meaning:** REST endpoints exchange representations, commonly JSON, over HTTP.
- **Used here:** `AnalysisController.java` accepts request records and returns result objects.
- **Tiny example:** POST a JSON body to `/api/v1/analysis/sms`.
- **Common mistake:** Sending malformed JSON or assuming a 200 response means the analysis is definitive.

## Important boundary
The MySQL schema in `database/schema.sql` is a design artefact. The current backend does not use JPA or persist analysis results to that database. Do not claim JPA, authentication, Android functionality, or live threat intelligence as implemented.
