-- Roles and permissions are reference data the application needs in every
-- environment (they used to exist only in the demo seed, so production had none).
-- Idempotent: rows already created by the seed are left as they are.

INSERT INTO permissions (code, description) VALUES
('ADMIN_FULL', 'Acceso completo de administrador'),
('USER_CREATE', 'Crear usuarios'),
('USER_EDIT', 'Editar usuarios'),
('USER_DELETE', 'Eliminar usuarios'),
('BOOK_CREATE', 'Agregar libros'),
('BOOK_EDIT', 'Editar libros'),
('BOOK_DELETE', 'Eliminar libros'),
('LOAN_CREATE', 'Crear préstamos'),
('LOAN_EDIT', 'Editar préstamos'),
('LOAN_RETURN', 'Procesar devoluciones'),
('FINE_CREATE', 'Crear multas'),
('FINE_EDIT', 'Editar multas'),
('FINE_PAYMENT', 'Procesar pagos de multas'),
('REPORT_VIEW', 'Ver reportes'),
('CATALOG_MANAGE', 'Gestionar catálogo'),
('RESERVATION_MANAGE', 'Gestionar reservas'),
('READER_MANAGE', 'Registrar y editar lectores')
ON CONFLICT (code) DO NOTHING;

INSERT INTO roles (name, description) VALUES
('Administrador', 'Acceso completo al sistema, gestión de usuarios y configuración'),
('Bibliotecario', 'Gestión de préstamos, devoluciones, multas y catalogación de libros'),
('Asistente', 'Apoyo en tareas básicas de biblioteca y atención al público')
ON CONFLICT (name) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM (VALUES
    ('Administrador', 'ADMIN_FULL'), ('Administrador', 'USER_CREATE'), ('Administrador', 'USER_EDIT'),
    ('Administrador', 'USER_DELETE'), ('Administrador', 'BOOK_CREATE'), ('Administrador', 'BOOK_EDIT'),
    ('Administrador', 'BOOK_DELETE'), ('Administrador', 'LOAN_CREATE'), ('Administrador', 'LOAN_EDIT'),
    ('Administrador', 'LOAN_RETURN'), ('Administrador', 'FINE_CREATE'), ('Administrador', 'FINE_EDIT'),
    ('Administrador', 'FINE_PAYMENT'), ('Administrador', 'REPORT_VIEW'), ('Administrador', 'CATALOG_MANAGE'),
    ('Administrador', 'RESERVATION_MANAGE'), ('Administrador', 'READER_MANAGE'),
    ('Bibliotecario', 'BOOK_CREATE'), ('Bibliotecario', 'BOOK_EDIT'), ('Bibliotecario', 'LOAN_CREATE'),
    ('Bibliotecario', 'LOAN_EDIT'), ('Bibliotecario', 'LOAN_RETURN'), ('Bibliotecario', 'FINE_CREATE'),
    ('Bibliotecario', 'FINE_EDIT'), ('Bibliotecario', 'FINE_PAYMENT'), ('Bibliotecario', 'REPORT_VIEW'),
    ('Bibliotecario', 'CATALOG_MANAGE'), ('Bibliotecario', 'RESERVATION_MANAGE'), ('Bibliotecario', 'READER_MANAGE'),
    ('Asistente', 'LOAN_CREATE'), ('Asistente', 'LOAN_RETURN'), ('Asistente', 'REPORT_VIEW'),
    ('Asistente', 'RESERVATION_MANAGE'), ('Asistente', 'READER_MANAGE')
) AS grant_(role_name, permission_code)
JOIN roles r ON r.name = grant_.role_name
JOIN permissions p ON p.code = grant_.permission_code
ON CONFLICT DO NOTHING;
