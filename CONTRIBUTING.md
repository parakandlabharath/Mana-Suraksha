# Contributing

Thanks for helping improve Mana Suraksha.

## Workflow
1. Open an issue describing the change.
2. Create a focused branch, e.g. feat/url-parser.
3. Make a small, reviewable change.
4. Add or update tests.
5. Run mvn test in core-java for Core Java changes.
6. Review the diff for secrets, personal data and unrelated edits.
7. Commit clearly and open a pull request.

## Commit prefixes
feat: new functionality; fix: bug fix; docs: documentation; test: tests; refactor: restructuring; build: build/CI changes.

## Safety rules
Never commit OTPs, PINs, passwords, API keys or database credentials. Use synthetic test data. Do not publish victims' private messages, phone numbers or unredacted evidence. Do not present community reports as verified facts or add automatic URL opening.

## Code expectations
Prefer clear names, small methods, validation at boundaries, tests for edge cases and Core Java domain logic independent from Spring infrastructure.
