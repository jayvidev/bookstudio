-- Spring Modulith event publication registry (schema v2 of spring-modulith-events-jdbc).
-- Events to other modules are stored in the same transaction that publishes them
-- and marked complete once their listener succeeds: a failed listener leaves the
-- event here to be retried (transactional outbox).
CREATE TABLE event_publication (
    id                     UUID                     NOT NULL,
    listener_id            TEXT                     NOT NULL,
    event_type             TEXT                     NOT NULL,
    serialized_event       TEXT                     NOT NULL,
    publication_date       TIMESTAMP WITH TIME ZONE NOT NULL,
    completion_date        TIMESTAMP WITH TIME ZONE,
    status                 TEXT,
    completion_attempts    INT,
    last_resubmission_date TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);
CREATE INDEX event_publication_serialized_event_hash_idx ON event_publication USING hash (serialized_event);
CREATE INDEX event_publication_by_completion_date_idx ON event_publication (completion_date);

-- At most one fine per loan item. Overdue fines are issued automatically when an
-- item is returned late; this keeps a redelivered event from fining twice.
CREATE UNIQUE INDEX ux_fines_loan_item ON fines (loan_id, copy_id);
