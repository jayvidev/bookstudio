-- Optimistic locking: concurrent changes to the same copy or loan fail instead of
-- silently overwriting each other (JPA @Version).
ALTER TABLE copies ADD COLUMN version BIGINT NOT NULL DEFAULT 0;
ALTER TABLE loans  ADD COLUMN version BIGINT NOT NULL DEFAULT 0;

-- A copy can be in at most one active loan item. The application enforces this
-- through the copy status; the database guarantees it even if that check races.
CREATE UNIQUE INDEX ux_loan_items_active_copy
    ON loan_items (copy_id)
    WHERE status IN ('PRESTADO', 'RETRASADO');
