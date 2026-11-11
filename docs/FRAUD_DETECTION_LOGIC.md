# Fraud Detection Logic

**Status:** Educational prototype rules; not a validated production detector.

## Result states
LOW: no strong signal found among checks performed. SUSPICIOUS: concerning signals found; verify independently. HIGH: multiple strong indicators or a trusted high-confidence match. UNKNOWN: insufficient data or an essential check unavailable.

## SMS candidate indicators
| Indicator | Example | Caution |
|---|---|---|
| Urgency | Act immediately or account closes | Legitimate alerts can be urgent |
| Credential request | Requests OTP/PIN/password disclosure | Strong signal when disclosure is requested |
| Payment pressure | Unexpected fee/refund transfer | Context matters |
| Impersonation | Claims to represent a bank/government | Brand name alone is not proof |
| Suspicious link | Unexpected shortened/lookalike domain | Short URLs are not automatically malicious |

A keyword match alone must not label a message fraudulent.

## URL indicators
Inspect scheme, hostname, punycode/IDN, lookalike characters, misleading subdomains, IP-literal hosts, user-info fields, unusual ports and approved reputation feeds. Never open the URL automatically.

## Phone reputation
Display verified, community-reported, no-known-reports or unknown. Caller ID can be spoofed and numbers can be reassigned.

## Score policy
Illustrative bands only: 0–29 low indicators, 30–69 suspicious, 70–100 high. Calibrate with labelled examples and measure false positives/negatives. A score is not a probability.

## Evaluation
Build a synthetic labelled test set with malicious-looking and benign examples. Measure precision, recall, false-positive and false-negative rates by language and input type. Review Telugu examples with fluent speakers. Community reports must not instantly create verified indicators.
