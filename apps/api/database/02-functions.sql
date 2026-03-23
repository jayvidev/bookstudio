SET search_path TO public;

CREATE OR REPLACE FUNCTION generate_reader_code()
RETURNS TRIGGER AS $$
DECLARE
    new_code TEXT;
    seq INT;
BEGIN
    SELECT COALESCE(MAX(SUBSTRING(code FROM 10)::INT), 0) + 1
    INTO seq
    FROM readers
    WHERE SUBSTRING(code FROM 5 FOR 4) = TO_CHAR(CURRENT_DATE, 'YYYY');

    new_code := 'LEC-' || TO_CHAR(CURRENT_DATE, 'YYYY') || '-' || LPAD(seq::TEXT, 5, '0');
    NEW.code := new_code;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_copy_code()
RETURNS TRIGGER AS $$
DECLARE
    new_code TEXT;
    seq INT;
BEGIN
    SELECT COALESCE(MAX(SUBSTRING(code FROM 9)::INT), 0) + 1
    INTO seq
    FROM copies
    WHERE SUBSTRING(code FROM 4 FOR 4) = TO_CHAR(CURRENT_DATE, 'YYYY');

    new_code := 'EJ-' || TO_CHAR(CURRENT_DATE, 'YYYY') || '-' || LPAD(seq::TEXT, 4, '0');
    NEW.code := new_code;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_loan_code()
RETURNS TRIGGER AS $$
DECLARE
    new_code TEXT;
    seq INT;
    date_part TEXT;
BEGIN
    date_part := TO_CHAR(NEW.loan_date, 'YYYYMMDD');

    SELECT COALESCE(MAX(SUBSTRING(code FROM 13)::INT), 0) + 1
    INTO seq
    FROM loans
    WHERE SUBSTRING(code FROM 5 FOR 8) = date_part;

    new_code := 'PRE-' || date_part || '-' || LPAD(seq::TEXT, 5, '0');
    NEW.code := new_code;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_reservation_code()
RETURNS TRIGGER AS $$
DECLARE
    new_code TEXT;
    seq INT;
BEGIN
    SELECT COALESCE(MAX(SUBSTRING(code FROM 10)::INT), 0) + 1
    INTO seq
    FROM reservations
    WHERE SUBSTRING(code FROM 5 FOR 4) = TO_CHAR(NEW.reservation_date, 'YYYY');

    new_code := 'RES-' || TO_CHAR(NEW.reservation_date, 'YYYY') || '-' || LPAD(seq::TEXT, 5, '0');
    NEW.code := new_code;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_fine_code()
RETURNS TRIGGER AS $$
DECLARE
    new_code TEXT;
    seq INT;
BEGIN
    SELECT COALESCE(MAX(SUBSTRING(code FROM 11)::INT), 0) + 1
    INTO seq
    FROM fines
    WHERE SUBSTRING(code FROM 6 FOR 4) = TO_CHAR(NEW.issued_at, 'YYYY');

    new_code := 'MULT-' || TO_CHAR(NEW.issued_at, 'YYYY') || '-' || LPAD(seq::TEXT, 5, '0');
    NEW.code := new_code;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_payment_code()
RETURNS TRIGGER AS $$
DECLARE
    new_code TEXT;
    seq INT;
BEGIN
    SELECT COALESCE(MAX(SUBSTRING(code FROM 10)::INT), 0) + 1
    INTO seq
    FROM payments
    WHERE SUBSTRING(code FROM 5 FOR 4) = TO_CHAR(NEW.payment_date, 'YYYY');

    new_code := 'PAG-' || TO_CHAR(NEW.payment_date, 'YYYY') || '-' || LPAD(seq::TEXT, 5, '0');
    NEW.code := new_code;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
