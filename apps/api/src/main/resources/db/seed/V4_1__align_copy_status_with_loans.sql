-- Demo data only. The original seed set copy statuses independently of loans;
-- align them with the active loan items now that loans keep them in sync.
UPDATE copies c
SET status = 'PRESTADO'
WHERE c.status <> 'PRESTADO'
  AND EXISTS (SELECT 1 FROM loan_items li
              WHERE li.copy_id = c.id AND li.status IN ('PRESTADO', 'RETRASADO'));

UPDATE copies c
SET status = 'DISPONIBLE'
WHERE c.status = 'PRESTADO'
  AND NOT EXISTS (SELECT 1 FROM loan_items li
                  WHERE li.copy_id = c.id AND li.status IN ('PRESTADO', 'RETRASADO'));
