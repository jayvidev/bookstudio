-- Every business code now follows PREFIX-YYYY-NNNNN: a three-letter prefix, the year
-- the counter restarts on, and a five-digit zero-padded counter.
--   loans:  PRE-YYYYMMDD-NNNNN -> PRE-YYYY-NNNNN  (yearly instead of daily counter)
--   copies: EJ-YYYY-NNNN       -> EJE-YYYY-NNNNN
--   fines:  MULT-YYYY-NNNNN    -> MUL-YYYY-NNNNN
-- Existing rows are rewritten because the system has not been released yet; the
-- other series already match the format.

UPDATE loans l
SET code = 'PRE-' || to_char(l.loan_date, 'YYYY') || '-' || lpad(n.counter::TEXT, 5, '0')
FROM (
    SELECT id, row_number() OVER (PARTITION BY date_part('year', loan_date) ORDER BY loan_date, id) AS counter
    FROM loans
) n
WHERE n.id = l.id;

UPDATE copies
SET code = 'EJE-' || split_part(code, '-', 2) || '-' || lpad(split_part(code, '-', 3), 5, '0');

UPDATE fines
SET code = 'MUL-' || split_part(code, '-', 2) || '-' || split_part(code, '-', 3);

DELETE FROM code_sequences;

INSERT INTO code_sequences (series, period, last_value)
SELECT series, period, MAX(counter)
FROM (
    SELECT split_part(code, '-', 1) AS series, split_part(code, '-', 2) AS period, split_part(code, '-', 3)::BIGINT AS counter FROM readers
    UNION ALL
    SELECT split_part(code, '-', 1), split_part(code, '-', 2), split_part(code, '-', 3)::BIGINT FROM copies
    UNION ALL
    SELECT split_part(code, '-', 1), split_part(code, '-', 2), split_part(code, '-', 3)::BIGINT FROM loans
    UNION ALL
    SELECT split_part(code, '-', 1), split_part(code, '-', 2), split_part(code, '-', 3)::BIGINT FROM reservations
    UNION ALL
    SELECT split_part(code, '-', 1), split_part(code, '-', 2), split_part(code, '-', 3)::BIGINT FROM fines
    UNION ALL
    SELECT split_part(code, '-', 1), split_part(code, '-', 2), split_part(code, '-', 3)::BIGINT FROM payments
) existing
GROUP BY series, period;

ALTER TABLE code_sequences ALTER COLUMN period TYPE VARCHAR(4);
