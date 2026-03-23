SET search_path TO public;

CREATE TRIGGER trg_generate_reader_code
BEFORE INSERT ON readers
FOR EACH ROW
WHEN (NEW.code IS NULL)
EXECUTE FUNCTION generate_reader_code();

CREATE TRIGGER trg_generate_copy_code
BEFORE INSERT ON copies
FOR EACH ROW
WHEN (NEW.code IS NULL)
EXECUTE FUNCTION generate_copy_code();

CREATE TRIGGER trg_generate_loan_code
BEFORE INSERT ON loans
FOR EACH ROW
WHEN (NEW.code IS NULL)
EXECUTE FUNCTION generate_loan_code();

CREATE TRIGGER trg_generate_reservation_code
BEFORE INSERT ON reservations
FOR EACH ROW
WHEN (NEW.code IS NULL)
EXECUTE FUNCTION generate_reservation_code();

CREATE TRIGGER trg_generate_fine_code
BEFORE INSERT ON fines
FOR EACH ROW
WHEN (NEW.code IS NULL)
EXECUTE FUNCTION generate_fine_code();

CREATE TRIGGER trg_generate_payment_code
BEFORE INSERT ON payments
FOR EACH ROW
WHEN (NEW.code IS NULL)
EXECUTE FUNCTION generate_payment_code();