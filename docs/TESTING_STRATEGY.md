# Testing Strategy

**Status:** Proposed test plan.

## Core Java unit tests
- SMS urgency, impersonation and credential disclosure requests.
- Benign SMS containing “OTP” without asking for disclosure.
- Empty, null, oversized and malformed inputs.
- URL invalid schemes, malformed URLs, Unicode/punycode, deceptive hosts and IP literals.
- Phone normalisation with different region contexts.
- Risk-policy boundaries, unknown state and stale intelligence.
- Report state transitions and duplicate handling.
- Telugu/English message-key coverage.

## Backend integration tests
- Request validation and response schema.
- Persistence and transaction boundaries.
- Report ownership and moderator authorisation.
- Provider timeout, failure and stale-cache behaviour.
- Rate limits, duplicate submissions and evidence access.

## Security tests
- No raw SMS text, full URL tokens or credentials in logs.
- SQL injection and output encoding checks.
- SSRF protections if URL fetching is introduced.
- Unauthorised report/evidence access denied.
- Moderator actions audited.
- Secret scanning and dependency checks.

## Client tests
- Large text, screen reader and contrast.
- Telugu/English translation review.
- Weak/offline connectivity and retry states.
- Android call-screening permissions and role support on real devices.

Use synthetic data only. Never publish real victims' messages, phone numbers or unredacted evidence.
