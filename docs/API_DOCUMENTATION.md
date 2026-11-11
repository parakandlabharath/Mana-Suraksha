# API Contract Proposal

**Status:** Proposed; endpoints are not yet implemented. Base path: /api/v1.

## Analysis
| Method | Endpoint | Purpose |
|---|---|---|
| POST | /analysis/sms | Analyse user-submitted message text |
| POST | /analysis/url | Analyse URL structure/reputation |
| POST | /analysis/phone | Check phone reputation |

Example SMS request:
```json
{
  "messageText": "Your account will be blocked. Share your OTP immediately.",
  "language": "en"
}
```

Example response:
```json
{
  "analysisId": "generated-id",
  "inputType": "SMS",
  "riskLevel": "HIGH",
  "riskScore": 82,
  "verificationStatus": "UNVERIFIED",
  "reasons": ["URGENT_ACCOUNT_THREAT", "OTP_DISCLOSURE_REQUEST"],
  "recommendations": ["Do not share your OTP, PIN or password."],
  "limitations": ["Automated assessment; not proof of sender identity."],
  "policyVersion": "1.0"
}
```

The client must warn users not to submit OTPs, passwords, PINs or account secrets. Request bodies must never be written to logs.

## Reports
| Method | Endpoint | Access |
|---|---|---|
| POST | /reports | Authenticated user |
| GET | /reports/{reportId} | Owner or moderator |
| GET | /reports/mine | Authenticated user |
| POST | /reports/{reportId}/evidence | Owner/moderator |
| GET | /moderation/reports | Moderator |
| PATCH | /moderation/reports/{reportId} | Moderator |

Other: GET /safety-guides/{guideCode}; GET /health (restricted operational endpoint).

## HTTP status conventions
400 invalid input; 401 unauthenticated; 403 forbidden; 404 missing resource; 409 state conflict; 413 upload too large; 429 rate limited; 5xx unexpected server failure.

Use DTOs, validate all fields, enforce ownership and moderator roles server-side, paginate collections, apply rate limits and never expose internal database entities or private reporter details.
