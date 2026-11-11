# Database Design

**Status:** Initial relational design proposal. SQL starter: database/schema.sql.

## Relationships
- One user has many analysis-history entries.
- One analysis has many findings.
- One user submits many fraud reports.
- One report has many evidence references and moderation actions.
- Users and roles are many-to-many through user_roles.
- Users and report votes are many-to-many through report_votes.
- Threat indicators are separate from reports. Moderation must explicitly approve an indicator before it is treated as verified.

## Tables
| Table | Purpose |
|---|---|
| users | User profile and preferred language |
| roles / user_roles | Role-based access |
| analysis_history | Minimal metadata about checks |
| analysis_findings | Rule codes and explanation codes |
| threat_indicators | Approved, sourced, expiring indicators |
| fraud_reports | Citizen reports and status |
| report_evidence | Private evidence object references |
| report_votes | Abuse-controlled community feedback |
| moderation_actions | Review decisions and reasons |
| audit_events | Security-sensitive actions |

Avoid raw SMS text and full URLs. Never store OTPs, PINs, passwords or banking credentials. Protect lookup identifiers and keep keys outside the database. Evidence belongs in private object storage. Add retention/deletion policies, restrict moderator access, index lookups and use transactions for report/moderation updates.
