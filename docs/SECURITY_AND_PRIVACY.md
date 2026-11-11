# Security and Privacy Baseline

**Status:** Design checklist; not a security certification.

## Data minimisation
- Do not request OTPs, PINs, passwords, card security codes or banking credentials.
- SMS text is supplied only through deliberate user action in v1.
- Do not store raw message text or full URLs by default; URL query strings can contain session tokens.
- Explain what is sent to the backend and any third-party reputation provider.

## Safe URL handling
- Parse URLs; do not navigate to them automatically.
- Reject unsupported schemes and malformed input.
- Do not fetch remote content in the first version.
- Any future URL fetcher must be isolated and defend against SSRF, private IP ranges, DNS rebinding, redirect abuse and oversized responses.

## Application security
- HTTPS/TLS, secure authentication and server-side authorisation.
- Least-privilege moderator/admin roles.
- Parameterised queries, output encoding, input validation and rate limits.
- Secrets supplied through environment/secret management, never committed to Git.
- No sensitive request bodies or credentials in logs.
- Audit moderator decisions and administrative changes.

## Reports and evidence
- Community reports are not verified facts.
- Detect duplicates and abuse; allow review, expiry and appeals.
- Keep reporter identity private.
- Evidence storage must be private, access-controlled, size-limited and malware-scanned.
- Redact personal data and set retention limits.

## Honest user messaging
A risk score is not proof or a statistical probability. “No known threat” is not a guarantee. Caller ID may be spoofed. If reputation data is stale or unavailable, show an explicit unknown/incomplete result.

## Release checklist
- [ ] Threat model reviewed.
- [ ] Sensitive-data logging tests pass.
- [ ] Authentication and authorisation tests pass.
- [ ] Rate limiting and upload restrictions configured.
- [ ] Retention/deletion policy documented.
- [ ] Dependency and secret scans enabled.
- [ ] Privacy notice and consent wording reviewed.
- [ ] Android call-screening behaviour tested on supported devices.
