# Project Workflow

**Status:** Planning baseline; implementation is in progress. Mana Suraksha helps people, especially first-time smartphone users and rural communities, recognise suspicious SMS messages, links and scam-call signals, report suspected fraud and take safer next steps.

## First release
- SMS: user pastes or deliberately shares selected text; analyse urgency, impersonation, credential/OTP requests and payment pressure.
- Links: parse URL structure and reputation without automatically opening the destination.
- Calls: manual phone-number reputation lookup. Android call screening is a later opt-in feature subject to OS support and permissions.
- Reports: submit suspected incidents, attach optional redacted evidence and track moderation status.
- Guidance: Telugu and English explanations and safety instructions.
- Clients: Android and web; backend: Java/Spring Boot; database: MySQL.

## User journey: SMS
1. User opens Check a message and pastes selected text.
2. App warns users to remove OTPs, PINs, passwords and account secrets.
3. API validates input; raw message text is not written to logs.
4. SMS analyzer extracts structured findings.
5. Risk policy returns a category, reasons, limitations and recommendations.
6. User can separately submit a report.

## User journey: link
1. User pastes/shares a URL and is told not to open it to test it.
2. Analyzer parses scheme and hostname without navigating to it.
3. Local checks inspect punycode, lookalike hostnames, IP-literal hosts and suspicious URL structure.
4. Optional reputation source is queried with minimum necessary data.
5. Result explains source freshness and limitations.

## User journey: phone/call
1. User enters a phone number or eligible Android call-screening service receives a permitted event.
2. Number is normalised with region context.
3. Approved indicators and moderated community reports are checked.
4. Result distinguishes verified, community-reported, no-known-reports and unknown.
5. Any warning/rejection action is user-configured and only used where Android supports it.

## User journey: report
1. User chooses category and enters a short description.
2. Optional evidence is redacted and stored privately.
3. API validates, rate-limits and creates a SUBMITTED report.
4. Moderator reviews evidence, duplicates and source reliability.
5. Moderator records decision and reason in an audit trail.
6. Status changes to UNDER_REVIEW, NEEDS_INFORMATION, VERIFIED, UNVERIFIED, DUPLICATE or CLOSED.
7. Only approved indicators become verified threat intelligence.

## Actors
| Actor | Capabilities |
|---|---|
| Citizen | Check content, read guidance, submit/view own reports |
| Volunteer | Assist a citizen with consent |
| Moderator | Review reports and record evidence-based decisions |
| Administrator | Manage roles, indicators and configuration |

## Functional requirements
| ID | Requirement |
|---|---|
| FR-01 | Core navigation and warnings available in Telugu and English |
| FR-02 | SMS analysis returns risk, reasons and next steps |
| FR-03 | URL analysis never auto-opens a submitted URL |
| FR-04 | Phone lookup displays source and verification state |
| FR-05 | Report submission returns a reference ID |
| FR-06 | User can track own report |
| FR-07 | Moderator decisions are audited |
| FR-08 | No bare unexplained risk score |
| FR-09 | Secrets are not requested or retained by default |
| FR-10 | Provider outages produce a clear unknown/incomplete result |

## Delivery milestones
M0 blueprint; M1 Core Java foundation; M2 SMS engine; M3 URL engine; M4 risk aggregation; M5 phone/report domain; M6 Spring Boot API and MySQL; M7 web client; M8 Android client; M9 security, privacy, accessibility and release testing.

## Git workflow
Create a focused branch, implement one task, add tests, run the build, inspect the diff for secrets, commit with a meaningful prefix (feat, fix, docs, test), push and open a pull request.

## Definition of done
Code, tests, documentation and UI behaviour must agree. A design document does not mean a feature is implemented. Use synthetic test data; never commit OTPs, credentials, private victim messages or unredacted evidence.

## Interview pitch
“Mana Suraksha is a bilingual digital-safety platform. Its modular Java engine analyses suspicious SMS messages, URLs and phone-number reputation, then returns an explainable risk assessment. A Spring Boot backend exposes analysis and reporting APIs, MySQL stores report metadata and moderated indicators, and Android/web clients provide accessible interfaces. The design separates community reports from verified threat intelligence and prioritises privacy and false-positive prevention.”

At the blueprint stage, describe planned components as planned—not completed.
