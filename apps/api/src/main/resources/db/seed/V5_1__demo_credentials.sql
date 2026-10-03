-- Demo data only. The original seed had placeholder hashes nobody could log in
-- with. Every demo staff account now uses the documented demo password
-- ("BookStudio2026!", BCrypt cost 12), and a "demo" account backs the
-- one-click demo login.
UPDATE workers SET password = '$2y$12$YwBJskL4h0kSH7dU0n5y2ehpb3SerDQDjRzTJMQIJ/PytESPm.tNC';

INSERT INTO workers (username, email, first_name, last_name, password, role_id, status)
SELECT 'demo', 'demo@bookstudio.pe', 'Cuenta', 'Demo', '$2y$12$YwBJskL4h0kSH7dU0n5y2ehpb3SerDQDjRzTJMQIJ/PytESPm.tNC', r.id, 'ACTIVO'
FROM roles r
WHERE r.name = 'Bibliotecario'
ON CONFLICT (username) DO NOTHING;
