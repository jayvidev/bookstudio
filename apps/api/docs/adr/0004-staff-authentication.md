# ADR 0004: Staff authentication with JWT and permission-based authorization

- **Status:** Accepted
- **Date:** 2026-10-03

## Context

The API was open: anyone could read, create and change any record. Staff
accounts (`workers`), roles and permissions already existed in the schema but
were not used; new workers' passwords were stored in plain text and the seeded
hashes were placeholders. Roles and permissions only existed in the demo seed,
so a production database would have had none.

## Decision

### Authentication

- **OAuth2 resource server** (Spring Security + Nimbus) validates bearer JWTs:
  HS256 signature, expiry and issuer. No hand-written token filter.
- **Access token:** 15 minutes. Claims: `sub` (username), `uid`, `role` and
  `permissions` (the role's permission codes, which become the request's
  authorities). Short-lived because it cannot be revoked.
- **Refresh token:** 256 random bits, opaque, 7 days. Only its SHA-256 hash is
  stored (`refresh_tokens`). Every refresh **rotates** it; presenting a revoked
  token is treated as theft and revokes all of that worker's sessions. The
  revocation is committed even though the response is 401
  (`noRollbackFor`).
- **Login** answers the same 401 for an unknown user, a wrong password or an
  inactive account, and checks a dummy BCrypt hash when the user does not
  exist so both paths take the same time (no user enumeration).
- **Passwords:** BCrypt, cost 12.
- **Rate limit:** token bucket per client IP on `/auth/login`, `/auth/demo` and
  `/auth/refresh` (10/min). The IP is `getRemoteAddr()`; behind a proxy
  `server.forward-headers-strategy` resolves it, never a raw
  `X-Forwarded-For` header that clients can forge.
- **Secret:** `app.security.jwt.secret` has no default; production must set
  `APP_SECURITY_JWT_SECRET` or the application does not start.

### Authorization

- Permissions come from the database (`roles` -> `role_permissions` ->
  `permissions`), now shipped as reference data in a migration (V5).
- Each module declares its rules in an `AuthorizationRules` bean next to its
  controllers (`POST /loans` needs `LOAN_CREATE`, ...). `SecurityConfig` only
  collects them, so it does not need to know every module's paths.
- Rules run in the **filter chain**, not as `@PreAuthorize`: method security
  runs after the request body has been validated, which would let a caller
  without permission see validation errors (400) instead of 403.
- Reads need an authenticated staff member; staff management needs
  `USER_*`; changing roles needs `ADMIN_FULL`.

### Demo

`POST /auth/demo` signs in as the `demo` account (Bibliotecario role) when
`app.demo.enabled` is true (local and docker profiles). Writes are allowed so
the demo is useful; a nightly database reset is planned for the deployed demo.

## Alternatives considered

- **Hand-written JWT filter with jjwt** (as in Orderly): more code to own and
  test for what the resource server already does.
- **Server-side sessions:** simpler revocation, but the frontend is deployed
  separately and a stateless API scales and deploys more simply.
- **Refresh token in an HttpOnly cookie set by the API:** the API and the web
  app live on different sites, which needs `SameSite=None` cookies and CSRF
  protection. Planned instead: the Next.js server (BFF) keeps the refresh
  token in its own HttpOnly cookie and calls this API with bearer tokens.
- **Asymmetric keys (RS256):** needed once another service must verify tokens
  without being able to issue them; one service does not need it yet.

## Consequences

- Every endpoint except health, auth and (non-prod) API docs requires a token;
  the frontend needs a login flow before it can call the API again.
- Permission changes reach a user at their next token (at most 15 minutes).
- `AuthorizationMatrixIntegrationTest` pins which permission every write needs.
