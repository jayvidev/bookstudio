SET search_path TO public;

BEGIN;

CREATE TABLE nationalities (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    code VARCHAR(2) UNIQUE NOT NULL
);

CREATE TABLE genres (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE categories (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    level VARCHAR(20) CHECK (level IN ('PRIMARIA', 'SECUNDARIA', 'SUPERIOR', 'GENERAL')),
    description TEXT,
    status VARCHAR(10) DEFAULT 'ACTIVO' CHECK (status IN ('ACTIVO', 'INACTIVO'))
);

CREATE TABLE languages (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    code VARCHAR(2) UNIQUE NOT NULL
);

CREATE TABLE authors (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    nationality_id BIGINT NOT NULL,
    birth_date DATE NOT NULL CHECK (birth_date >= '1000-01-01' AND birth_date <= CURRENT_DATE),
    biography TEXT,
    status VARCHAR(10) DEFAULT 'ACTIVO' CHECK (status IN ('ACTIVO', 'INACTIVO')),
    photo_url VARCHAR(1024),
    FOREIGN KEY (nationality_id) REFERENCES nationalities(id)
);

CREATE TABLE publishers (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    nationality_id BIGINT NOT NULL,
    foundation_year INT NOT NULL CHECK (foundation_year >= 1400 AND foundation_year <= DATE_PART('year', CURRENT_DATE)),
    website VARCHAR(255),
    address VARCHAR(255),
    status VARCHAR(10) DEFAULT 'ACTIVO' CHECK (status IN ('ACTIVO', 'INACTIVO')),
    photo_url VARCHAR(1024),
    FOREIGN KEY (nationality_id) REFERENCES nationalities(id)
);

CREATE TABLE readers (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(50) UNIQUE,
    dni VARCHAR(8) UNIQUE NOT NULL CHECK (dni ~ '^[0-9]{8}$'),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(9) NOT NULL CHECK (phone ~ '^[0-9]{9}$'),
    email VARCHAR(100) UNIQUE NOT NULL,
    birth_date DATE NOT NULL CHECK (birth_date >= '1900-01-01' AND birth_date <= CURRENT_DATE),
    gender VARCHAR(10) NOT NULL CHECK (gender IN ('MASCULINO', 'FEMENINO')),
    type VARCHAR(15) NOT NULL CHECK (type IN ('ESTUDIANTE', 'DOCENTE', 'ADMINISTRATIVO', 'EXTERNO')),
    status VARCHAR(15) DEFAULT 'ACTIVO' CHECK (status IN ('ACTIVO', 'SUSPENDIDO', 'BLOQUEADO', 'ELIMINADO'))
);

CREATE TABLE books (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    language_id BIGINT NOT NULL,
    edition VARCHAR(50),
    pages INT CHECK (pages > 0),
    description TEXT,
    cover_url VARCHAR(300),
    publisher_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    release_date DATE NOT NULL CHECK (release_date <= CURRENT_DATE),
    status VARCHAR(10) DEFAULT 'ACTIVO' CHECK (status IN ('ACTIVO', 'INACTIVO')),
    FOREIGN KEY (language_id) REFERENCES languages(id),
    FOREIGN KEY (publisher_id) REFERENCES publishers(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE book_authors (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

CREATE TABLE book_genres (
    book_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE publisher_genres (
    publisher_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (publisher_id, genre_id),
    FOREIGN KEY (publisher_id) REFERENCES publishers(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE locations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE shelves (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    location_id BIGINT NOT NULL,
    code VARCHAR(50) NOT NULL,
    floor VARCHAR(50),
    description TEXT,
    UNIQUE (location_id, code),
    FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE
);

CREATE TABLE copies (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(50) UNIQUE,
    book_id BIGINT NOT NULL,
    shelf_id BIGINT NOT NULL,
    barcode VARCHAR(50) UNIQUE NOT NULL,
    status VARCHAR(15) DEFAULT 'DISPONIBLE' CHECK (status IN ('DISPONIBLE', 'PRESTADO', 'RESERVADO', 'EXTRAVIADO', 'MANTENIMIENTO')),
    condition VARCHAR(15) DEFAULT 'BUENO' CHECK (condition IN ('NUEVO', 'BUENO', 'REGULAR', 'MALO', 'DETERIORADO')),
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (shelf_id) REFERENCES shelves(id)
);

CREATE TABLE loans (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(50) UNIQUE,
    reader_id BIGINT NOT NULL,
    loan_date DATE NOT NULL,
    observation TEXT,
    FOREIGN KEY (reader_id) REFERENCES readers(id)
);

CREATE TABLE loan_items (
    loan_id BIGINT NOT NULL,
    copy_id BIGINT NOT NULL,
    due_date DATE,
    return_date DATE,
    status VARCHAR(15) DEFAULT 'PRESTADO' CHECK (status IN ('PRESTADO', 'DEVUELTO', 'RETRASADO', 'EXTRAVIADO', 'CANCELADO')),
    PRIMARY KEY (loan_id, copy_id),
    FOREIGN KEY (loan_id) REFERENCES loans(id) ON DELETE CASCADE,
    FOREIGN KEY (copy_id) REFERENCES copies(id)
);

CREATE TABLE roles (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE permissions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

CREATE TABLE workers (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL,
    profile_photo_url VARCHAR(1024),
    status VARCHAR(15) DEFAULT 'ACTIVO' CHECK (status IN ('ACTIVO', 'SUSPENDIDO', 'ELIMINADO')),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE reservations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(50) UNIQUE,
    reader_id BIGINT NOT NULL,
    copy_id BIGINT NOT NULL,
    reservation_date DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(15) DEFAULT 'PENDIENTE' CHECK (status IN ('PENDIENTE', 'CANCELADA', 'ATENDIDA', 'EXPIRADA')),
    FOREIGN KEY (reader_id) REFERENCES readers(id),
    FOREIGN KEY (copy_id) REFERENCES copies(id)
);

CREATE TABLE fines (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(50) UNIQUE,
    loan_id BIGINT NOT NULL,
    copy_id BIGINT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL CHECK (amount >= 0),
    days_late INT NOT NULL CHECK (days_late >= 1),
    issued_at DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(15) DEFAULT 'PENDIENTE' CHECK (status IN ('PENDIENTE', 'PAGADO', 'CONDONADO')),
    FOREIGN KEY (loan_id, copy_id) REFERENCES loan_items(loan_id, copy_id) ON DELETE CASCADE
);

CREATE TABLE payments (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(50) UNIQUE,
    reader_id BIGINT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL CHECK (amount > 0),
    payment_date DATE NOT NULL DEFAULT CURRENT_DATE,
    method VARCHAR(15) NOT NULL CHECK (method IN ('EFECTIVO', 'TARJETA', 'TRANSFERENCIA', 'CHEQUE', 'OTROS')),
    FOREIGN KEY (reader_id) REFERENCES readers(id)
);

CREATE TABLE payment_fines (
    payment_id BIGINT NOT NULL,
    fine_id BIGINT NOT NULL,
    PRIMARY KEY (payment_id, fine_id),
    FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE,
    FOREIGN KEY (fine_id) REFERENCES fines(id) ON DELETE CASCADE
);

COMMIT;