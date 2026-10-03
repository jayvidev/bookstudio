-- Opaque refresh tokens. Only a SHA-256 hash is stored, so a database leak does
-- not leak usable tokens. Tokens are rotated on every use; presenting a revoked
-- token is treated as theft and revokes the whole family of that worker.
CREATE TABLE refresh_tokens (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    worker_id  BIGINT       NOT NULL REFERENCES workers (id) ON DELETE CASCADE,
    token_hash VARCHAR(64)  NOT NULL UNIQUE,
    expires_at TIMESTAMPTZ  NOT NULL,
    revoked_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX ix_refresh_tokens_active_by_worker ON refresh_tokens (worker_id) WHERE revoked_at IS NULL;
