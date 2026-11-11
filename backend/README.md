# Backend API — Planned

This directory is reserved for the Spring Boot REST API. The backend has not been implemented yet.

## Planned responsibilities
- REST controllers for SMS, URL, phone and report endpoints.
- DTO validation and safe error responses.
- Authentication, role-based authorisation and rate limiting.
- Application services that call the framework-independent Core Java module.
- MySQL repositories and database migrations.
- Audit events and private evidence-storage integration.

## Planned package layout
- controller — HTTP endpoints
- dto — request/response models
- service — application use cases
- repository — persistence interfaces
- security — authentication and authorisation
- config — application configuration

Keep secrets outside Git. Do not log request bodies or credentials. See the API and system-design documents in docs/.
