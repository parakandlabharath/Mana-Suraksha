# Development Workflow

## End-to-end request flow
1. Citizen selects SMS, URL or phone in the web/Android client.
2. Client validates input and warns against sharing secrets.
3. Client sends JSON to the Spring Boot REST API.
4. Controller validates the request and invokes a Core Java analyzer.
5. Analyzer returns structured findings and an assessment.
6. API serialises the result; client displays reasons, limitations and safe next steps.
7. Citizen may separately submit a report. A report is not a verified threat until reviewed by a moderator.

## Run locally
Requirements: JDK 17+, Maven 3.8+, Git.

From repository root:
```bash
mvn test
mvn -pl backend -am install
mvn -pl backend spring-boot:run
```
API: http://localhost:8080. Health: GET /api/v1/health.

Serve the web client from the web-app directory:
```bash
python -m http.server 5500
```
Open http://localhost:5500. Browser-to-API CORS configuration is still required before the browser client can call the backend; do not use wildcard CORS in production.

## Test API manually
```bash
curl -X POST http://localhost:8080/api/v1/analysis/sms -H "Content-Type: application/json" -d '{"text":"Please share your OTP immediately"}'
curl -X POST http://localhost:8080/api/v1/analysis/url -H "Content-Type: application/json" -d '{"url":"http://192.0.2.10/login"}'
```

## GitHub workflow
```bash
git pull origin main
git switch -c feat/short-description
# edit source and add tests
mvn test
git status
git diff
git add .
git commit -m "feat: describe change"
git push -u origin feat/short-description
```
Then open a pull request. Never commit secrets or real victim data.

## Current implementation boundary
Implemented starter: Core Java SMS rules, URL structure checks, Indian phone-number normalisation, Spring Boot analysis and health endpoints, a basic browser interface and initial SQL schema. Not implemented: database integration, authentication, reports/moderation API, external threat feeds, Telugu UI translations, production fraud models, Android client and call screening.
