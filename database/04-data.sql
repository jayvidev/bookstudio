SET search_path TO public;

BEGIN;

-- 1. NACIONALIDADES
INSERT INTO nationalities (name, code) VALUES
('Peruana', 'pe'),
('Española', 'es'),
('Argentina', 'ar'),
('Mexicana', 'mx'),
('Colombiana', 'co'),
('Chilena', 'cl'),
('Estadounidense', 'us'),
('Británica', 'gb'),
('Francesa', 'fr'),
('Alemana', 'de'),
('Italiana', 'it'),
('Brasileña', 'br'),
('Japonesa', 'jp'),
('Rusa', 'ru'),
('Canadiense', 'ca'),
('Australiana', 'au'),
('Holandesa', 'nl'),
('Sueca', 'se'),
('Noruega', 'no'),
('Danesa', 'dk'),
('Belga', 'be'),
('Suiza', 'ch'),
('Austriaca', 'at'),
('Polaca', 'pl'),
('Checa', 'cz');

-- 2. GÉNEROS LITERARIOS
INSERT INTO genres (name) VALUES 
('Novela'),
('Cuento'),
('Poesía'),
('Drama'),
('Ensayo'),
('Biografía'),
('Historia'),
('Ciencia Ficción'),
('Fantasía'),
('Misterio'),
('Romance'),
('Aventura'),
('Terror'),
('Autoayuda'),
('Técnico'),
('Educativo'),
('Infantil'),
('Juvenil'),
('Policiaco'),
('Histórica'),
('Contemporánea'),
('Clásica'),
('Realismo Mágico'),
('Distopía'),
('Utopía');

-- 3. IDIOMAS
INSERT INTO languages (name, code) VALUES 
('Español', 'es'),
('Inglés', 'en'),
('Francés', 'fr'),
('Alemán', 'de'),
('Italiano', 'it'),
('Portugués', 'pt'),
('Japonés', 'ja'),
('Ruso', 'ru'),
('Chino', 'zh'),
('Árabe', 'ar'),
('Hindi', 'hi'),
('Coreano', 'ko'),
('Holandés', 'nl'),
('Sueco', 'sv'),
('Noruego', 'no'),
('Danés', 'da'),
('Finés', 'fi'),
('Griego', 'el'),
('Latín', 'la'),
('Hebreo', 'he');

-- 4. CATEGORÍAS
INSERT INTO categories (name, level, description, status) VALUES 
('Literatura Clásica', 'SUPERIOR', 'Obras literarias de reconocido valor universal', 'ACTIVO'),
('Literatura Contemporánea', 'SUPERIOR', 'Obras literarias modernas y actuales', 'ACTIVO'),
('Historia del Perú', 'SECUNDARIA', 'Libros sobre la historia nacional', 'ACTIVO'),
('Historia Universal', 'SECUNDARIA', 'Historia mundial y civilizaciones', 'ACTIVO'),
('Matemáticas', 'SECUNDARIA', 'Libros de matemáticas nivel secundario', 'ACTIVO'),
('Física', 'SECUNDARIA', 'Textos de física para secundaria', 'ACTIVO'),
('Química', 'SECUNDARIA', 'Libros de química nivel secundario', 'ACTIVO'),
('Biología', 'SECUNDARIA', 'Textos de biología y ciencias naturales', 'ACTIVO'),
('Cuentos Infantiles', 'PRIMARIA', 'Libros para niños de educación primaria', 'ACTIVO'),
('Enciclopedias', 'GENERAL', 'Obras de consulta general', 'ACTIVO'),
('Filosofía', 'SUPERIOR', 'Textos filosóficos y pensamiento', 'ACTIVO'),
('Psicología', 'SUPERIOR', 'Libros de psicología y comportamiento humano', 'ACTIVO'),
('Ingeniería', 'SUPERIOR', 'Textos técnicos de ingeniería', 'ACTIVO'),
('Medicina', 'SUPERIOR', 'Libros de medicina y salud', 'ACTIVO'),
('Derecho', 'SUPERIOR', 'Textos jurídicos y legales', 'ACTIVO'),
('Geografía', 'SECUNDARIA', 'Libros de geografía y cartografía', 'ACTIVO'),
('Economía', 'SUPERIOR', 'Textos de economía y finanzas', 'ACTIVO'),
('Sociología', 'SUPERIOR', 'Estudios sociales y antropología', 'ACTIVO'),
('Arte', 'GENERAL', 'Historia del arte y técnicas artísticas', 'ACTIVO'),
('Música', 'GENERAL', 'Teoría musical e historia de la música', 'ACTIVO'),
('Deportes', 'GENERAL', 'Libros sobre deportes y educación física', 'ACTIVO'),
('Cocina', 'GENERAL', 'Libros de recetas y gastronomía', 'ACTIVO'),
('Jardinería', 'GENERAL', 'Cuidado de plantas y jardines', 'ACTIVO'),
('Tecnología', 'SUPERIOR', 'Informática y nuevas tecnologías', 'ACTIVO'),
('Religión', 'GENERAL', 'Textos religiosos y estudios teológicos', 'ACTIVO'),

-- CATEGORÍAS CON ESTADO INACTIVO
('Literatura Fantástica', 'GENERAL', 'Novelas de fantasía y ciencia ficción', 'INACTIVO'),
('Astronomía', 'SECUNDARIA', 'Libros sobre el universo y los astros', 'INACTIVO'),
('Política Internacional', 'SUPERIOR', 'Análisis político y relaciones internacionales', 'INACTIVO'),
('Administración', 'SUPERIOR', 'Gestión empresarial y organizacional', 'INACTIVO'),
('Química Orgánica', 'SUPERIOR', 'Química especializada nivel universitario', 'INACTIVO'),
('Pedagogía', 'SUPERIOR', 'Métodos de enseñanza y educación', 'INACTIVO'),
('Teatro Clásico', 'SECUNDARIA', 'Obras teatrales tradicionales', 'INACTIVO'),
('Antropología Cultural', 'SUPERIOR', 'Estudios de culturas y sociedades', 'INACTIVO'),
('Botánica', 'SUPERIOR', 'Estudio científico de las plantas', 'INACTIVO'),
('Arqueología', 'SUPERIOR', 'Investigación de civilizaciones antiguas', 'INACTIVO'),
('Literatura Juvenil', 'SECUNDARIA', 'Libros dirigidos a adolescentes', 'INACTIVO'),
('Estadística', 'SUPERIOR', 'Métodos estadísticos y análisis de datos', 'INACTIVO'),
('Criminología', 'SUPERIOR', 'Estudio del crimen y la justicia penal', 'INACTIVO'),
('Nutrición', 'SUPERIOR', 'Alimentación y salud nutricional', 'INACTIVO'),
('Periodismo', 'SUPERIOR', 'Técnicas de comunicación y medios', 'INACTIVO'),
('Turismo', 'SUPERIOR', 'Gestión turística y hotelera', 'INACTIVO'),
('Ecología', 'GENERAL', 'Medio ambiente y conservación', 'INACTIVO'),
('Folklore', 'GENERAL', 'Tradiciones y cultura popular', 'INACTIVO'),
('Fotografía', 'GENERAL', 'Técnicas fotográficas y composición', 'INACTIVO'),
('Ajedrez', 'GENERAL', 'Estrategias y técnicas del juego de ajedrez', 'INACTIVO');

-- 5. AUTORES
INSERT INTO authors (name, nationality_id, birth_date, biography, status, photo_url) VALUES 
-- Autores peruanos
('Mario Vargas Llosa', 1, '1936-03-28', 'Premio Nobel de Literatura 2010, escritor peruano considerado uno de los más importantes novelistas contemporáneos', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1vrnEOMvV7mwmCjPaPQkFluklQO_IdsMvaQ&s'),
('Julio Ramón Ribeyro', 1, '1929-08-31', 'Escritor peruano, maestro del cuento latinoamericano', 'ACTIVO', 'https://portal.andina.pe/EDPfotografia2/Thumbnail/2011/12/03/000171146W.jpg'),
('César Vallejo', 1, '1892-03-16', 'Poeta peruano, considerado uno de los mayores innovadores de la poesía del siglo XX', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIcaENo1dT84TEQhcP9ypMGTEHSe8zeGz3sQ&s'),
('José María Arguedas', 1, '1911-01-18', 'Escritor y antropólogo peruano, gran conocedor de la cultura andina', 'ACTIVO', 'https://static.wixstatic.com/media/1f8451_ab7e05f2b05b409f97b2a7b8a48265bf~mv2.jpg/v1/fill/w_420,h_607,al_c,lg_1,q_80/1f8451_ab7e05f2b05b409f97b2a7b8a48265bf~mv2.jpg'),
('Ricardo Palma', 1, '1833-02-07', 'Escritor peruano, creador de las Tradiciones Peruanas', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-5xsn7BPM5X9Wyjfs_5cwWJ7-wUJqlbT9oQ&s'),
('Ciro Alegría', 1, '1909-11-04', 'Novelista peruano, autor de El mundo es ancho y ajeno', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqyqMpscC4JQHXuuJ_FNep3N6DAU12CijIXQ&s'),
('José Carlos Mariátegui', 1, '1894-06-14', 'Ensayista y político peruano, fundador del pensamiento marxista en Perú', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoxReV53nsHY0Z6IDLkUZkZ65SsT4RVLd5yg&s'),
('Alfredo Bryce Echenique', 1, '1939-02-19', 'Escritor peruano contemporáneo, autor de Un mundo para Julius', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMmUkZHafO_SU9YnccrNc6WH_2U8FREHPAsQ&s'),

-- Autores latinoamericanos
('Gabriel García Márquez', 5, '1927-03-06', 'Escritor colombiano, Premio Nobel de Literatura 1982, máximo exponente del realismo mágico', 'ACTIVO', 'https://www.biografiasyvidas.com/reportaje/garcia_marquez/fotos/garcia_marquez_420a.jpg'),
('Jorge Luis Borges', 3, '1899-08-24', 'Escritor argentino, maestro del cuento fantástico y la literatura universal', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHKIGlaz3swY7Dz4dPvHbS2Vefa4m0auvY2g&s'),
('Isabel Allende', 3, '1942-08-02', 'Escritora chilena, una de las novelistas más leídas del mundo', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKKHLqOoWoChMh08Mrh6ypo295IMQEzyAUWA&s'),
('Octavio Paz', 4, '1914-03-31', 'Poeta y ensayista mexicano, Premio Nobel de Literatura 1990', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNh6HAYvG6Nf0eCwhUamJR6DooIhX_f7mqhQ&s'),
('Julio Cortázar', 3, '1914-08-26', 'Escritor argentino, maestro del cuento fantástico', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAxnGUOOqgregKFvtRdEAlujiGq2PX2MeS5Q&s'),
('Pablo Neruda', 6, '1904-07-12', 'Poeta chileno, Premio Nobel de Literatura 1971', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTwxSPB9FTat7um_b6KRiCGOHFUyRKbgWSxw&s'),
('Carlos Fuentes', 4, '1928-11-11', 'Escritor mexicano, figura clave del boom latinoamericano', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB0tz84SUKi8ofB4-PGBO1pawbo8Uk029-Vw&s'),
('Ernesto Sabato', 3, '1911-06-24', 'Escritor argentino, autor de El túnel', 'INACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTW3L_tRYM8_zKZxBqmEsecUcz5l1LwAkKBBQ&s'),

-- Autores españoles
('Miguel de Cervantes', 2, '1547-09-29', 'Escritor español, autor de Don Quijote de la Mancha', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQptVFHhSDXKQF-Jg04BV9m_ebL46ofOeJqGg&s'),
('Federico García Lorca', 2, '1898-06-05', 'Poeta y dramaturgo español de la Generación del 27', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxIvPX4QM3R_jz0BLZ8tikBYkoVJ5hCD9jmQ&s'),
('Antonio Machado', 2, '1875-07-26', 'Poeta español de la Generación del 98', 'ACTIVO', 'https://www.cervantesvirtual.com/images/portales/antonio_machado/antonio_machado.jpg'),
('Juan Ramón Jiménez', 2, '1881-12-23', 'Poeta español, Premio Nobel de Literatura 1956', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2MAHA1q7KckmnQrhJmVxLQDMdcXKYPZ-JVQ&s'),
('Camilo José Cela', 2, '1916-05-11', 'Escritor español, Premio Nobel de Literatura 1989', 'INACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUMhqvroPXoeZqUhFLxGTOQ2NvfYjHClVW_g&s'),
('Miguel Delibes', 2, '1920-10-17', 'Novelista español, autor de Los santos inocentes', 'INACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8XmYugB0jtGO4A6n_T940MEXT9ASYHsHRBA&s'),

-- Autores anglosajones
('William Shakespeare', 8, '1564-04-26', 'Dramaturgo y poeta inglés, considerado el escritor más importante en lengua inglesa', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfaNYWLuCHQbKlAe-G7AkVoD76qvBsvwR4Vg&s'),
('Ernest Hemingway', 7, '1899-07-21', 'Escritor estadounidense, Premio Nobel de Literatura 1954', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQr-h9MaDue5peCMC0nHtjfPd6rK-flGwMq2Q&s'),
('Stephen King', 7, '1947-09-21', 'Escritor estadounidense, maestro del terror y suspense', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzdidYzM4lPIVjiSBk_zyLYE1JmSCt_NZMFA&s'),
('J.K. Rowling', 8, '1965-07-31', 'Escritora británica, autora de la saga Harry Potter', 'ACTIVO', 'https://m.media-amazon.com/images/S/amzn-author-media-prod/8cigckin175jtpsk3gs361r4ss.jpg'),
('Agatha Christie', 8, '1890-09-15', 'Escritora británica, reina del misterio y la novela policíaca', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVox9E3jQeH8BFpnBRANQeBBC0bsH9230x6Q&s'),
('George Orwell', 8, '1903-06-25', 'Escritor británico, autor de 1984 y Rebelión en la granja', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWtPv8flPnqjFjXD4f26XftUHQNg3WvmBBCgqGPDQQmNzZZfTWVgTIk07ioArjBAskgI8'),
('Jane Austen', 8, '1775-12-16', 'Novelista británica, autora de Orgullo y prejuicio', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSa_DJhO_MYLBPWthlaG-T3VS1s9XDajHewYw&s'),
('Charles Dickens', 8, '1812-02-07', 'Novelista británico, autor de Oliver Twist', 'ACTIVO', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Dickens_Gurney_head.jpg/330px-Dickens_Gurney_head.jpg'),
('Mark Twain', 7, '1835-11-30', 'Escritor estadounidense, autor de Las aventuras de Tom Sawyer', 'ACTIVO', 'https://www.biografiasyvidas.com/biografia/t/fotos/twain_mark.jpg'),
('Edgar Allan Poe', 7, '1809-01-19', 'Escritor estadounidense, maestro del relato corto y la poesía', 'ACTIVO', 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Edgar_Allan_Poe_portrait.jpg/250px-Edgar_Allan_Poe_portrait.jpg');

-- 6. EDITORIALES
INSERT INTO publishers (name, nationality_id, foundation_year, website, address, status, photo_url) VALUES 
('Planeta', 2, 1949, 'www.planeta.es', 'Av. Diagonal 662-664, Barcelona, España', 'ACTIVO', NULL),
('Alfaguara', 2, 1964, 'www.alfaguara.com', 'Calle Torrelaguna 60, Madrid, España', 'ACTIVO', NULL),
('Penguin Random House', 7, 1927, 'www.penguinrandomhouse.com', 'New York, Estados Unidos', 'ACTIVO', NULL),
('Santillana', 2, 1960, 'www.santillana.com', 'Madrid, España', 'ACTIVO', NULL),
('Anagrama', 2, 1969, 'www.anagrama-ed.es', 'Barcelona, España', 'INACTIVO', NULL),
('Fondo de Cultura Económica', 4, 1934, 'www.fondodeculturaeconomica.com', 'Ciudad de México, México', 'ACTIVO', NULL),
('Seix Barral', 2, 1911, 'www.seixbarral.es', 'Barcelona, España', 'ACTIVO', NULL),
('Cátedra', 2, 1973, 'www.catedra.com', 'Madrid, España', 'ACTIVO', NULL),
('Tusquets', 2, 1969, 'www.tusquetseditores.com', 'Barcelona, España', 'INACTIVO', NULL),
('Peisa', 1, 1962, 'www.peisa.com.pe', 'Lima, Perú', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTL5wYUc7Sdg3cfgS7eKvaaDBX6xpxD2mUEsg&s'),
('Editorial Norma', 5, 1960, 'www.librerianorma.com', 'Bogotá, Colombia', 'ACTIVO', 'https://pbs.twimg.com/profile_images/887441240110751744/mwP27QIB_400x400.jpg'),
('Editorial Sudamericana', 3, 1939, 'www.sudamericanalibros.com.ar', 'Buenos Aires, Argentina', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQijPZ8s7YKGiJUWwnBZqojCS0rfCWUw6d1Tw&s'),
('Emecé Editores', 3, 1939, 'www.emece.com.ar', 'Buenos Aires, Argentina', 'INACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqIiS61_tgWiFOKdEiulmL8jZZkt0f6wbj6Q&s'),
('Editorial Porrúa', 4, 1900, 'www.porrua.com', 'Ciudad de México, México', 'ACTIVO', 'https://http2.mlstatic.com/D_NQ_NP_718679-MLM31408428279_072019-O.webp'),
('Editorial Andrés Bello', 6, 1843, 'www.andresbello.cl', 'Santiago, Chile', 'ACTIVO', 'https://es.babelio.com/users/EDIG_Andres-Bello_9631.jpg'),
('Salamandra', 2, 2000, 'www.salamandra.info', 'Barcelona, España', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyiLwCL41nSe1jcHg4uee2dCZyBUGr4HS3bg&s'),
('Debolsillo', 2, 1995, 'www.penguinlibros.com', 'Barcelona, España', 'ACTIVO', 'https://hablemos-escritoras-as.s3.us-west-1.amazonaws.com/variants/wgnm367hidp8j4s1pmefduijvyfv/fec9312db351f781c0197cc9c4f9c66013b1e39507ba1aecf24862b67be970f0'),
('Crítica', 2, 1976, 'www.ed-critica.es', 'Barcelona, España', 'ACTIVO', 'https://casatomadalibreriacafe.com/wp-content/uploads/2020/09/logo_CRITICA.jpg'),
('Alianza Editorial', 2, 1966, 'www.alianzaeditorial.es', 'Madrid, España', 'INACTIVO', 'https://pbs.twimg.com/profile_images/1369193039466553345/bmhVCrT7_400x400.jpg'),
('Ediciones B', 2, 1986, 'www.edicionesb.com', 'Barcelona, España', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-Z5B40zh6694uqvZB6XbpLHkJ5dPbV_TuK0VhPrw5WtwfcNM3eZ1M3agJR6czbcKlz38');

-- 7. ROLES
INSERT INTO roles (name, description) VALUES 
('Administrador', 'Acceso completo al sistema, gestión de usuarios y configuración'),
('Bibliotecario', 'Gestión de préstamos, devoluciones, multas y catalogación de libros'),
('Asistente', 'Apoyo en tareas básicas de biblioteca y atención al público');

-- 8. PERMISOS
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
('RESERVATION_MANAGE', 'Gestionar reservas');

-- 9. ASIGNACIÓN DE PERMISOS A ROLES
INSERT INTO role_permissions (role_id, permission_id) VALUES 
-- Administrador (todos los permisos)
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), 
(1, 11), (1, 12), (1, 13), (1, 14), (1, 15), (1, 16),
-- Bibliotecario (permisos operativos)
(2, 5), (2, 6), (2, 8), (2, 9), (2, 10), (2, 11), (2, 12), (2, 13), (2, 14), (2, 15), (2, 16),
-- Asistente (permisos básicos)
(3, 8), (3, 10), (3, 14), (3, 16);

-- 10. TRABAJADORES
INSERT INTO workers (username, email, first_name, last_name, password, role_id, status) VALUES 
('admin', 'admin@biblioteca.edu.pe', 'Carlos', 'Mendoza García', '$2b$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewfzUf0wdG8OuJdm', 1, 'ACTIVO'),
('bibliotecario1', 'ana.torres@biblioteca.edu.pe', 'Ana María', 'Torres Vásquez', '$2b$12$EixZxQz5UbrYdHqgqHl/JO4nrpxY/PnZsT0YnUGf3rGNrGNrGNrGN', 2, 'ACTIVO'),
('bibliotecario2', 'jose.ruiz@biblioteca.edu.pe', 'José Luis', 'Ruiz Morales', '$2b$12$HQqO8vTYmlnKEuTQSrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 2, 'ACTIVO'),
('bibliotecario3', 'lucia.santos@biblioteca.edu.pe', 'Lucía', 'Santos Rivera', '$2b$12$HQqO8vTYmlnKEuTQSrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 2, 'ELIMINADO'),
('bibliotecario4', 'pedro.martinez@biblioteca.edu.pe', 'Pedro', 'Martínez Castillo', '$2b$12$HQqO8vTYmlnKEuTQSrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 2, 'ACTIVO'),
('asistente1', 'maria.lopez@biblioteca.edu.pe', 'María Elena', 'López Fernández', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'ACTIVO'),
('asistente2', 'carmen.diaz@biblioteca.edu.pe', 'Carmen Rosa', 'Díaz Huamán', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'ACTIVO'),
('asistente3', 'ricardo.flores@biblioteca.edu.pe', 'Ricardo', 'Flores Pérez', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'ACTIVO'),
('asistente4', 'sofia.guerrero@biblioteca.edu.pe', 'Sofía', 'Guerrero Vilca', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'ACTIVO'),
('asistente5', 'miguel.rojas@biblioteca.edu.pe', 'Miguel', 'Rojas Condori', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'ACTIVO'),
('bibliotecario5', 'elena.vargas@biblioteca.edu.pe', 'Elena', 'Vargas Medina', '$2b$12$HQqO8vTYmlnKEuTQSrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 2, 'SUSPENDIDO'),
('bibliotecario6', 'fernando.silva@biblioteca.edu.pe', 'Fernando', 'Silva Coronado', '$2b$12$HQqO8vTYmlnKEuTQSrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 2, 'ACTIVO'),
('asistente6', 'andrea.mamani@biblioteca.edu.pe', 'Andrea', 'Mamani Quispe', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'ACTIVO'),
('asistente7', 'carlos.herrera@biblioteca.edu.pe', 'Carlos Alberto', 'Herrera Luna', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'ACTIVO'),
('bibliotecario7', 'patricia.ramos@biblioteca.edu.pe', 'Patricia', 'Ramos Ccahuana', '$2b$12$HQqO8vTYmlnKEuTQSrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 2, 'ACTIVO'),
('asistente8', 'david.choque@biblioteca.edu.pe', 'David', 'Choque Apaza', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'ACTIVO'),
('bibliotecario8', 'rosa.morales@biblioteca.edu.pe', 'Rosa María', 'Morales Espinoza', '$2b$12$HQqO8vTYmlnKEuTQSrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 2, 'ACTIVO'),
('asistente9', 'jesus.poma@biblioteca.edu.pe', 'Jesús', 'Poma Hancco', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'SUSPENDIDO'),
('bibliotecario9', 'monica.alvarez@biblioteca.edu.pe', 'Mónica', 'Álvarez Tejada', '$2b$12$HQqO8vTYmlnKEuTQSrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 2, 'ACTIVO'),
('asistente10', 'luis.nina@biblioteca.edu.pe', 'Luis Fernando', 'Nina Calla', '$2b$12$QrNfmP5tTYoFEu5QNrToLeyH1aFPv2OfPzTgNfN.kL9vLkNrGNrGN', 3, 'ACTIVO');

-- 11. UBICACIONES
INSERT INTO locations (name, description) VALUES 
('Sala General', 'Sala principal con literatura general y consulta'),
('Sala de Estudio', 'Área silenciosa para estudio individual'),
('Hemeroteca', 'Sección de revistas y periódicos'),
('Sala Infantil', 'Área destinada a niños y literatura infantil'),
('Sala de Referencia', 'Enciclopedias y obras de consulta'),
('Depósito', 'Almacén de libros no disponibles para préstamo'),
('Sala de Investigación', 'Área especializada para investigadores'),
('Sala de Computadoras', 'Área con acceso a recursos digitales'),
('Recepción', 'Área de información y orientación'),
('Archivo Histórico', 'Documentos y libros antiguos'),
('Sala de Conferencias', 'Espacio para eventos académicos'),
('Cubículos de Estudio', 'Espacios individuales de estudio'),
('Área de Descanso', 'Zona de relajación y lectura informal'),
('Exposiciones', 'Área para exhibiciones temporales'),
('Ludoteca', 'Espacio de juegos educativos para niños'),
('Biblioteca Digital', 'Acceso a recursos electrónicos'),
('Auditorio', 'Espacio para conferencias y presentaciones'),
('Cafetería', 'Área de alimentación y socialización'),
('Talleres', 'Salas para actividades académicas'),
('Préstamos Especiales', 'Material audiovisual y multimedia');

-- 12. ESTANTES
INSERT INTO shelves (location_id, code, floor, description) VALUES 
-- Planta Baja - Sala General
(1, 'A01', 'Planta Baja', 'Literatura peruana'),
(1, 'A02', 'Planta Baja', 'Literatura latinoamericana'),
(1, 'A03', 'Planta Baja', 'Literatura española'),
(1, 'A04', 'Planta Baja', 'Literatura universal'),
(1, 'A05', 'Planta Baja', 'Novelas contemporáneas'),
(1, 'A06', 'Planta Baja', 'Clásicos universales'),
(1, 'B01', 'Planta Baja', 'Historia y biografías'),
(1, 'B02', 'Planta Baja', 'Ciencias sociales'),
(1, 'B03', 'Planta Baja', 'Filosofía'),
(1, 'B04', 'Planta Baja', 'Religión'),
-- Primer Piso - Sala de Estudio
(2, 'C01', 'Primer Piso', 'Matemáticas y física'),
(2, 'C02', 'Primer Piso', 'Química y biología'),
(2, 'C03', 'Primer Piso', 'Ingeniería y tecnología'),
(2, 'C04', 'Primer Piso', 'Medicina y salud'),
(2, 'C05', 'Primer Piso', 'Derecho'),
(2, 'C06', 'Primer Piso', 'Economía'),
(2, 'C07', 'Primer Piso', 'Psicología'),
(2, 'C08', 'Primer Piso', 'Educación'),
-- Segundo Piso - Sala Infantil
(4, 'D01', 'Segundo Piso', 'Cuentos infantiles'),
(4, 'D02', 'Segundo Piso', 'Literatura juvenil'),
(4, 'D03', 'Segundo Piso', 'Libros ilustrados'),
(4, 'D04', 'Segundo Piso', 'Fábulas y leyendas'),
(4, 'D05', 'Segundo Piso', 'Ciencias para niños'),
-- Segundo Piso - Sala de Referencia
(5, 'E01', 'Segundo Piso', 'Enciclopedias'),
(5, 'E02', 'Segundo Piso', 'Diccionarios y atlas'),
(5, 'E03', 'Segundo Piso', 'Manuales técnicos'),
(5, 'E04', 'Segundo Piso', 'Guías especializadas'),
-- Tercer Piso - Sala de Investigación
(7, 'F01', 'Tercer Piso', 'Tesis y monografías'),
(7, 'F02', 'Tercer Piso', 'Publicaciones académicas'),
(7, 'F03', 'Tercer Piso', 'Investigación histórica'),
(7, 'F04', 'Tercer Piso', 'Documentos especiales'),
-- Depósito
(6, 'G01', 'Depósito', 'Libros en reparación'),
(6, 'G02', 'Depósito', 'Colección reservada'),
(6, 'G03', 'Depósito', 'Duplicados'),
-- Hemeroteca
(3, 'H01', 'Primer Piso', 'Revistas científicas'),
(3, 'H02', 'Primer Piso', 'Periódicos nacionales'),
(3, 'H03', 'Primer Piso', 'Revistas de divulgación'),
-- Áreas especializadas
(8, 'I01', 'Primer Piso', 'Recursos digitales'),
(10, 'J01', 'Sótano', 'Archivo histórico'),
(16, 'K01', 'Tercer Piso', 'Colección digital'),
(20, 'L01', 'Segundo Piso', 'Material audiovisual'),
(19, 'M01', 'Primer Piso', 'Manuales de talleres'),
(15, 'N01', 'Segundo Piso', 'Juegos educativos'),
(14, 'O01', 'Planta Baja', 'Catálogos de exposición');

INSERT INTO books (
  title, isbn, language_id, edition, pages, description, 
  publisher_id, category_id, release_date, status, cover_url
) VALUES 
-- Literatura peruana
('La ciudad y los perros', '978-84-204-8304-7', 1, '1ª edición', 408, 'Primera novela de Mario Vargas Llosa, ambientada en el Colegio Militar Leoncio Prado', 2, 1, '1963-10-15', 'ACTIVO', 'https://www.rae.es/sites/default/files/styles/obra_portada_ficha/public/la_ciudad_y_los_perros.jpg?itok=xRoMNsUb'),
('Los ríos profundos', '978-84-376-0495-4', 1, '3ª edición', 264, 'Novela de José María Arguedas sobre la cultura andina', 7, 1, '1958-11-20', 'ACTIVO', NULL),
('La palabra del mudo', '978-84-204-8305-4', 1, '1ª edición', 520, 'Cuentos completos de Julio Ramón Ribeyro', 2, 2, '1973-08-15', 'ACTIVO', NULL),
('Trilce', '978-84-376-0496-1', 1, '4ª edición', 180, 'Poemario revolucionario de César Vallejo', 8, 3, '1922-10-15', 'ACTIVO', NULL),
('Tradiciones Peruanas', '978-84-376-0497-2', 1, '2ª edición', 450, 'Relatos costumbristas de Ricardo Palma', 10, 7, '1872-05-10', 'ACTIVO', 'https://www.elvirrey.com/imagenes/9789972/978997233296.GIF'),
('El mundo es ancho y ajeno', '978-84-376-0498-3', 1, '1ª edición', 380, 'Novela indigenista de Ciro Alegría', 10, 1, '1941-03-15', 'INACTIVO', NULL),
('7 ensayos de interpretación de la realidad peruana', '978-84-376-0499-4', 1, '5ª edición', 320, 'Obra fundamental de José Carlos Mariátegui', 6, 5, '1928-10-01', 'INACTIVO', NULL),
('Un mundo para Julius', '978-84-376-0500-6', 1, '2ª edición', 520, 'Novela de Alfredo Bryce Echenique', 2, 2, '1970-08-20', 'INACTIVO', NULL),

-- Literatura latinoamericana
('Cien años de soledad', '978-84-376-0494-7', 1, '2ª edición', 432, 'Obra maestra del realismo mágico de Gabriel García Márquez', 2, 1, '1967-05-30', 'ACTIVO', NULL),
('Ficciones', '978-84-376-0501-7', 1, '3ª edición', 280, 'Cuentos fantásticos de Jorge Luis Borges', 11, 2, '1944-12-15', 'ACTIVO', NULL),
('La casa de los espíritus', '978-84-376-0502-8', 1, '1ª edición', 465, 'Primera novela de Isabel Allende', 1, 1, '1982-04-10', 'ACTIVO', NULL),
('El laberinto de la soledad', '978-84-376-0503-9', 1, '4ª edición', 350, 'Ensayo de Octavio Paz sobre la identidad mexicana', 6, 5, '1950-07-15', 'INACTIVO', NULL),
('Rayuela', '978-84-376-0504-0', 1, '2ª edición', 635, 'Novela experimental de Julio Cortázar', 2, 1, '1963-06-28', 'ACTIVO', 'https://images.cdn3.buscalibre.com/fit-in/360x360/90/53/905322d10841b36aa311dbd5c90d92ed.jpg'),
('Veinte poemas de amor y una canción desesperada', '978-84-376-0505-1', 1, '6ª edición', 120, 'Poemario de Pablo Neruda', 7, 3, '1924-08-16', 'INACTIVO', NULL),
('La muerte de Artemio Cruz', '978-84-376-0506-2', 1, '1ª edición', 290, 'Novela de Carlos Fuentes', 6, 1, '1962-11-05', 'INACTIVO', NULL),
('El túnel', '978-84-376-0507-3', 1, '3ª edición', 158, 'Novela psicológica de Ernesto Sabato', 12, 1, '1948-09-20', 'INACTIVO', NULL),

-- Literatura española
('Don Quijote de la Mancha', '978-84-376-0497-8', 1, '5ª edición', 1200, 'Obra cumbre de la literatura española de Miguel de Cervantes', 8, 1, '1605-01-16', 'ACTIVO', NULL),
('Romancero Gitano', '978-84-376-0508-4', 1, '4ª edición', 180, 'Poemario de Federico García Lorca', 8, 3, '1928-07-15', 'INACTIVO', NULL),
('Campos de Castilla', '978-84-376-0509-5', 1, '3ª edición', 220, 'Poesía de Antonio Machado', 8, 3, '1912-04-20', 'INACTIVO', NULL),
('Platero y yo', '978-84-376-0510-6', 1, '7ª edición', 150, 'Prosa poética de Juan Ramón Jiménez', 8, 3, '1914-12-25', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQ23tn2gq2hBnxcbTrFbytZp15_5V6iJ4w2w&s'),
('La colmena', '978-84-376-0511-7', 1, '2ª edición', 380, 'Novela de Camilo José Cela', 2, 1, '1951-03-15', 'INACTIVO', NULL),
('Los santos inocentes', '978-84-376-0512-8', 1, '1ª edición', 250, 'Novela de Miguel Delibes', 1, 1, '1981-10-12', 'INACTIVO', NULL),

-- Literatura universal
('Romeo y Julieta', '978-84-376-0498-5', 1, '2ª edición', 280, 'Tragedia romántica de William Shakespeare', 8, 4, '1597-03-20', 'ACTIVO', 'https://images.cdn2.buscalibre.com/fit-in/360x360/1b/3d/1b3d13ee1322464e244fbb0b23dc11db.jpg'),
('El viejo y el mar', '978-84-376-0499-2', 1, '3ª edición', 128, 'Novela corta de Ernest Hemingway, Premio Pulitzer', 2, 1, '1952-09-01', 'ACTIVO', NULL),
('It', '978-84-376-0513-9', 1, '1ª edición', 1138, 'Novela de terror de Stephen King', 3, 13, '1986-09-15', 'ACTIVO', NULL),
('Harry Potter y la piedra filosofal', '978-84-376-0500-5', 1, '1ª edición', 320, 'Primera novela de la saga de J.K. Rowling', 4, 9, '1997-06-26', 'ACTIVO', 'https://images.cdn3.buscalibre.com/fit-in/360x360/f5/f2/f5f2e2edd647784f07b709f1fef82d1c.jpg'),
('Asesinato en el Orient Express', '978-84-376-0501-2', 1, '2ª edición', 256, 'Novela de misterio de Agatha Christie con Hércules Poirot', 1, 10, '1934-01-01', 'ACTIVO', NULL),
('1984', '978-84-376-0514-0', 1, '4ª edición', 328, 'Distopía clásica de George Orwell', 19, 24, '1949-06-08', 'ACTIVO', 'https://images.cdn1.buscalibre.com/fit-in/360x360/b0/39/b039af065268818b7bd3b0e016f8db65.jpg'),
('Orgullo y prejuicio', '978-84-376-0515-1', 1, '3ª edición', 432, 'Novela romántica de Jane Austen', 3, 11, '1813-01-28', 'ACTIVO', 'https://pendulo.com/imagenes_grandes/9788494/978849441163.GIF'),
('Oliver Twist', '978-84-376-0516-2', 1, '2ª edición', 380, 'Novela social de Charles Dickens', 3, 1, '1838-02-14', 'INACTIVO', NULL),
('Las aventuras de Tom Sawyer', '978-84-376-0517-3', 1, '1ª edición', 285, 'Novela de aventuras de Mark Twain', 3, 12, '1876-12-10', 'INACTIVO', NULL),
('El cuervo y otros poemas', '978-84-376-0518-4', 1, '2ª edición', 95, 'Poesía de Edgar Allan Poe', 3, 3, '1845-01-29', 'INACTIVO', NULL),

-- Libros académicos y técnicos
('Historia del Perú Contemporáneo', '978-84-376-0502-9', 1, '1ª edición', 480, 'Historia del Perú desde la independencia hasta la actualidad', 10, 3, '2020-03-15', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTE7JKNvHH9z1dXPXQ1giHsfknDNlNoncsniA&s'),
('Matemáticas 4to Secundaria', '978-84-376-0503-6', 1, '2ª edición', 360, 'Texto escolar de matemáticas para cuarto año de secundaria', 4, 5, '2023-02-01', 'ACTIVO', NULL),
('Física General', '978-84-376-0504-3', 1, '3ª edición', 420, 'Principios fundamentales de física para educación secundaria', 4, 6, '2022-08-20', 'ACTIVO', NULL),
('Química Orgánica', '978-84-376-0519-5', 1, '1ª edición', 580, 'Fundamentos de química orgánica para nivel superior', 4, 7, '2021-09-10', 'ACTIVO', NULL),
('Biología Molecular', '978-84-376-0520-6', 1, '2ª edición', 650, 'Avances en biología molecular y celular', 4, 8, '2020-11-25', 'ACTIVO', 'https://images.cdn1.buscalibre.com/fit-in/360x360/45/d2/45d28890f2d269be7a105e4697931eb7.jpg'),
('Anatomía Humana', '978-84-376-0521-7', 1, '4ª edición', 780, 'Atlas completo de anatomía humana', 4, 14, '2019-07-18', 'ACTIVO', NULL),
('Código Civil Peruano', '978-84-376-0522-8', 1, '10ª edición', 450, 'Legislación civil vigente en el Perú', 10, 15, '2023-01-01', 'INACTIVO', 'https://www.sancristoballibros.com/imagenes/9786125/978612501923.GIF'),
('Principios de Economía', '978-84-376-0523-9', 1, '3ª edición', 520, 'Fundamentos de teoría económica', 6, 17, '2022-03-14', 'INACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlTpoDiln3Va1fIdQix4CfSoT_dljmOsTgHg&s'),
('Psicología del Desarrollo', '978-84-376-0524-0', 1, '2ª edición', 390, 'Desarrollo psicológico a lo largo de la vida', 1, 12, '2021-05-20', 'ACTIVO', 'https://images.cdn1.buscalibre.com/fit-in/360x360/83/e8/83e81530c5d2ed30544439f0d84b899e.jpg'),
('Sociología Contemporánea', '978-84-376-0525-1', 1, '1ª edición', 440, 'Teorías sociológicas actuales', 6, 18, '2020-09-30', 'INACTIVO', 'https://images.cdn1.buscalibre.com/fit-in/360x360/30/41/3041d48bf887e062392a1cf10e006d5f.jpg'),

-- Literatura infantil y juvenil
('El Principito', '978-84-376-0505-0', 1, '10ª edición', 96, 'Clásico de la literatura infantil y juvenil', 4, 9, '1943-04-06', 'ACTIVO', 'https://image.cdn1.buscalibre.com/5b57fc1690f0b5295a8b4567.RS500x500.jpg'),
('Matilda', '978-84-376-0526-2', 1, '3ª edición', 240, 'Novela infantil de Roald Dahl', 2, 17, '1988-04-01', 'ACTIVO', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQp_ZwohnRa6rntPD2M_AvydOmRyZ3ITtrbRA&s'),
('Charlie y la fábrica de chocolate', '978-84-376-0527-3', 1, '4ª edición', 180, 'Clásico infantil de Roald Dahl', 2, 17, '1964-09-12', 'INACTIVO', 'https://www.crisol.com.pe/media/catalog/product/cache/f6d2c62455a42b0d712f6c919e880845/9/7/9786124349164_4gd8k8wgb0tgpdgl.jpg'),
('Las crónicas de Narnia: El león, la bruja y el ropero', '978-84-376-0528-4', 1, '2ª edición', 208, 'Primera novela de la saga de C.S. Lewis', 16, 9, '1950-10-16', 'INACTIVO', 'https://images.cdn3.buscalibre.com/fit-in/360x360/76/08/7608d458499a573da01c43ebd8de9b22.jpg'),
('Donde viven los monstruos', '978-84-376-0529-5', 1, '5ª edición', 40, 'Libro ilustrado de Maurice Sendak', 4, 17, '1963-11-13', 'INACTIVO', 'https://imgv2-1-f.scribdassets.com/img/document/499209501/original/8b22de0346/1?v=1'),

-- Enciclopedias y referencias
('Enciclopedia Juvenil Oceano', '978-84-376-0506-7', 1, '1ª edición', 800, 'Enciclopedia temática para jóvenes estudiantes', 4, 10, '2021-01-15', 'ACTIVO', 'https://biblioteca.asamblea.gob.sv/backendsiab/images/portadas/Enciclopedia%20Juvenil%20Oceano.jpg'),
('Gran Enciclopedia Larousse', '978-84-376-0530-6', 1, '15ª edición', 1200, 'Enciclopedia universal de consulta', 19, 10, '2023-06-01', 'INACTIVO', 'https://librosateneo.com/image/cache/catalog/libros/lote-43/Scan-43-10-550x550.jpg'),
('Diccionario de la Real Academia Española', '978-84-376-0531-7', 1, '23ª edición', 2300, 'Diccionario oficial de la lengua española', 19, 10, '2022-10-15', 'ACTIVO', 'https://letras.rae.es/128-thickbox_default/diccionario-de-la-lengua-espanola-23-edicion.jpg'),
('Atlas Mundial', '978-84-376-0532-8', 1, '8ª edición', 350, 'Atlas geográfico mundial actualizado', 4, 16, '2023-02-28', 'INACTIVO', 'https://images.cdn1.buscalibre.com/fit-in/360x360/3c/59/3c59379c54fbdafd5428ed7935d77590.jpg'),
('Manual de Primeros Auxilios', '978-84-376-0533-9', 1, '6ª edición', 280, 'Guía práctica de primeros auxilios', 4, 14, '2022-11-10', 'INACTIVO', 'https://www.udllibros.com/imagenes/9788416/978841640712.JPG');

-- 14. RELACIÓN LIBROS-AUTORES
INSERT INTO book_authors (book_id, author_id) VALUES 
-- Literatura peruana
(1, 1),   -- La ciudad y los perros - Mario Vargas Llosa
(2, 4),   -- Los ríos profundos - José María Arguedas
(3, 2),   -- La palabra del mudo - Julio Ramón Ribeyro
(4, 3),   -- Trilce - César Vallejo
(5, 5),   -- Tradiciones Peruanas - Ricardo Palma
(6, 6),   -- El mundo es ancho y ajeno - Ciro Alegría
(7, 7),   -- 7 ensayos - José Carlos Mariátegui
(8, 8),   -- Un mundo para Julius - Alfredo Bryce Echenique

-- Literatura latinoamericana
(9, 9),   -- Cien años de soledad - Gabriel García Márquez
(10, 10), -- Ficciones - Jorge Luis Borges
(11, 11), -- La casa de los espíritus - Isabel Allende
(12, 12), -- El laberinto de la soledad - Octavio Paz
(13, 13), -- Rayuela - Julio Cortázar
(14, 14), -- Veinte poemas - Pablo Neruda
(15, 15), -- La muerte de Artemio Cruz - Carlos Fuentes
(16, 16), -- El túnel - Ernesto Sabato

-- Literatura española
(17, 17), -- Don Quijote - Miguel de Cervantes
(18, 18), -- Romancero Gitano - Federico García Lorca
(19, 19), -- Campos de Castilla - Antonio Machado
(20, 20), -- Platero y yo - Juan Ramón Jiménez
(21, 21), -- La colmena - Camilo José Cela
(22, 22), -- Los santos inocentes - Miguel Delibes

-- Literatura universal
(23, 23), -- Romeo y Julieta - William Shakespeare
(24, 24), -- El viejo y el mar - Ernest Hemingway
(25, 25), -- It - Stephen King
(26, 26), -- Harry Potter - J.K. Rowling
(27, 27), -- Asesinato en el Orient Express - Agatha Christie
(28, 28), -- 1984 - George Orwell
(29, 29), -- Orgullo y prejuicio - Jane Austen
(30, 30), -- Oliver Twist - Charles Dickens
(31, 31), -- Las aventuras de Tom Sawyer - Mark Twain
(32, 32); -- El cuervo - Edgar Allan Poe

-- 15. RELACIÓN LIBROS-GÉNEROS (expandido)
INSERT INTO book_genres (book_id, genre_id) VALUES 
-- Literatura peruana
(1, 1),     -- La ciudad y los perros - Novela
(2, 1),     -- Los ríos profundos - Novela
(3, 2),     -- La palabra del mudo - Cuento
(4, 3),     -- Trilce - Poesía
(5, 7),     -- Tradiciones Peruanas - Historia
(6, 1),     -- El mundo es ancho y ajeno - Novela
(7, 5),     -- 7 ensayos - Ensayo
(8, 1),     -- Un mundo para Julius - Novela

-- Literatura latinoamericana
(9, 1), (9, 23),  -- Cien años de soledad - Novela, Realismo Mágico
(10, 2), (10, 8), -- Ficciones - Cuento, Ciencia Ficción
(11, 1), (11, 23), -- La casa de los espíritus - Novela, Realismo Mágico
(12, 5),    -- El laberinto de la soledad - Ensayo
(13, 1),    -- Rayuela - Novela
(14, 3),    -- Veinte poemas - Poesía
(15, 1),    -- La muerte de Artemio Cruz - Novela
(16, 1),    -- El túnel - Novela

-- Literatura española
(17, 1), (17, 12),  -- Don Quijote - Novela, Aventura
(18, 3),    -- Romancero Gitano - Poesía
(19, 3),    -- Campos de Castilla - Poesía
(20, 3),    -- Platero y yo - Poesía
(21, 1),    -- La colmena - Novela
(22, 1),    -- Los santos inocentes - Novela

-- Literatura universal
(23, 4), (23, 11),  -- Romeo y Julieta - Drama, Romance
(24, 1),    -- El viejo y el mar - Novela
(25, 13),   -- It - Terror
(26, 9), (26, 18),  -- Harry Potter - Fantasía, Juvenil
(27, 10), (27, 19), -- Asesinato en el Orient Express - Misterio, Policiaco
(28, 8), (28, 24),  -- 1984 - Ciencia Ficción, Distopía
(29, 11), (29, 20), -- Orgullo y prejuicio - Romance, Histórica
(30, 1),    -- Oliver Twist - Novela
(31, 12), (31, 18), -- Las aventuras de Tom Sawyer - Aventura, Juvenil
(32, 3), (32, 13),  -- El cuervo - Poesía, Terror

-- Libros académicos
(33, 7),    -- Historia del Perú - Historia
(34, 16),   -- Matemáticas - Educativo
(35, 16),   -- Física - Educativo
(36, 16),   -- Química - Educativo
(37, 16),   -- Biología - Educativo
(38, 16),   -- Anatomía - Educativo
(39, 15),   -- Código Civil - Técnico
(40, 16),   -- Economía - Educativo
(41, 16),   -- Psicología - Educativo
(42, 16),   -- Sociología - Educativo

-- Literatura infantil
(43, 17),   -- El Principito - Infantil
(44, 17),   -- Matilda - Infantil
(45, 17),   -- Charlie y la fábrica - Infantil
(46, 9), (46, 17),  -- Narnia - Fantasía, Infantil
(47, 17);   -- Donde viven los monstruos - Infantil

-- 16. LECTORES
INSERT INTO readers (dni, first_name, last_name, address, phone, email, birth_date, gender, type, status) VALUES 
-- Estudiantes
('12345678', 'Ana María', 'García López', 'Av. Universitaria 1234, Lima', '987654321', 'ana.garcia@email.com', '1998-05-15', 'FEMENINO', 'ESTUDIANTE', 'ACTIVO'),
('23456789', 'Carlos Andrés', 'Mendoza Silva', 'Jr. Los Olivos 567, Lima', '976543210', 'carlos.mendoza@email.com', '1997-08-22', 'MASCULINO', 'ESTUDIANTE', 'ACTIVO'),
('56789012', 'Patricia Rosa', 'Vargas Chávez', 'Av. Brasil 456, Lima', '943210987', 'patricia.vargas@email.com', '1999-07-18', 'FEMENINO', 'ESTUDIANTE', 'ACTIVO'),
('67890123', 'Roberto Miguel', 'Castro Quispe', 'Jr. Junín 789, Lima', '932109876', 'roberto.castro@email.com', '2000-01-25', 'MASCULINO', 'ESTUDIANTE', 'ACTIVO'),
('90123456', 'Lucía Isabel', 'Paredes Gutiérrez', 'Av. Tacna 987, Lima', '901876543', 'lucia.paredes@email.com', '2001-09-30', 'FEMENINO', 'ESTUDIANTE', 'ACTIVO'),
('01234567', 'Diego Alejandro', 'Moreno Ramos', 'Jr. Huancayo 147, Lima', '890765432', 'diego.moreno@email.com', '1996-06-12', 'MASCULINO', 'ESTUDIANTE', 'ACTIVO'),
('11111111', 'Sofía Elena', 'Quispe Mamani', 'Av. La Marina 234, Lima', '987123456', 'sofia.quispe@email.com', '1999-03-08', 'FEMENINO', 'ESTUDIANTE', 'ACTIVO'),
('22222222', 'Javier Fernando', 'Huamán Flores', 'Jr. Lampa 890, Lima', '976234567', 'javier.huaman@email.com', '1998-11-20', 'MASCULINO', 'ESTUDIANTE', 'ACTIVO'),
('33333333', 'Camila Andrea', 'Torres Vilca', 'Av. Abancay 456, Lima', '965345678', 'camila.torres@email.com', '2000-02-14', 'FEMENINO', 'ESTUDIANTE', 'ACTIVO'),
('44444444', 'Mateo José', 'Condori Apaza', 'Jr. Ayacucho 123, Lima', '954456789', 'mateo.condori@email.com', '1997-12-03', 'MASCULINO', 'ESTUDIANTE', 'ACTIVO'),
('55555555', 'Valeria Milagros', 'Ramos Ccahuana', 'Av. Wilson 678, Lima', '943567890', 'valeria.ramos@email.com', '1999-08-17', 'FEMENINO', 'ESTUDIANTE', 'ACTIVO'),
('66666666', 'Sebastián Alonso', 'Poma Hancco', 'Jr. Cusco 345, Lima', '932678901', 'sebastian.poma@email.com', '1998-04-29', 'MASCULINO', 'ESTUDIANTE', 'ACTIVO'),
('77777777', 'Isabella Nicole', 'Nina Calla', 'Av. Grau 789, Lima', '921789012', 'isabella.nina@email.com', '2001-01-11', 'FEMENINO', 'ESTUDIANTE', 'ACTIVO'),
('88888888', 'Adrián Gabriel', 'Choque Apaza', 'Jr. Puno 234, Lima', '910890123', 'adrian.choque@email.com', '1996-09-25', 'MASCULINO', 'ESTUDIANTE', 'ACTIVO'),
('99999999', 'Fernanda Alejandra', 'Mamani Quispe', 'Av. Argentina 567, Lima', '909901234', 'fernanda.mamani@email.com', '1999-05-06', 'FEMENINO', 'ESTUDIANTE', 'ACTIVO'),

-- Docentes
('34567890', 'María Elena', 'Rodríguez Torres', 'Av. Arequipa 890, Lima', '965432109', 'maria.rodriguez@email.com', '1985-03-10', 'FEMENINO', 'DOCENTE', 'ACTIVO'),
('78901234', 'Carmen Luz', 'Herrera Morales', 'Av. Colonial 321, Lima', '921098765', 'carmen.herrera@email.com', '1988-11-14', 'FEMENINO', 'DOCENTE', 'ACTIVO'),
('12121212', 'Luis Alberto', 'Vega Sánchez', 'Av. El Sol 456, Lima', '987654321', 'luis.vega@email.com', '1982-07-22', 'MASCULINO', 'DOCENTE', 'ACTIVO'),
('13131313', 'Rosa María', 'Guerrero Silva', 'Jr. Lima 789, Lima', '976543210', 'rosa.guerrero@email.com', '1980-12-15', 'FEMENINO', 'DOCENTE', 'ACTIVO'),
('14141414', 'Jorge Enrique', 'Medina Castro', 'Av. Salaverry 123, Lima', '965432109', 'jorge.medina@email.com', '1975-04-18', 'MASCULINO', 'DOCENTE', 'ACTIVO'),

-- Administrativos
('45678901', 'José Luis', 'Fernández Huamán', 'Calle Las Flores 123, Lima', '954321098', 'jose.fernandez@email.com', '1990-12-03', 'MASCULINO', 'ADMINISTRATIVO', 'ACTIVO'),
('15151515', 'Sandra Patricia', 'López Vargas', 'Av. Petit Thouars 345, Lima', '954321098', 'sandra.lopez@email.com', '1987-06-28', 'FEMENINO', 'ADMINISTRATIVO', 'ACTIVO'),
('16161616', 'Ricardo Manuel', 'Morales Díaz', 'Jr. Ica 678, Lima', '943210987', 'ricardo.morales@email.com', '1983-02-09', 'MASCULINO', 'ADMINISTRATIVO', 'ACTIVO'),

-- Externos
('89012345', 'Manuel Antonio', 'Sánchez Vega', 'Calle San Martín 654, Lima', '910987654', 'manuel.sanchez@email.com', '1995-04-07', 'MASCULINO', 'EXTERNO', 'ACTIVO'),
('17171717', 'Pilar Roxana', 'Alvarez Tejada', 'Av. Canadá 234, Lima', '932109876', 'pilar.alvarez@email.com', '1992-10-13', 'FEMENINO', 'EXTERNO', 'ACTIVO'),
('18181818', 'Eduardo César', 'Espinoza Luna', 'Jr. Trujillo 567, Lima', '921098765', 'eduardo.espinoza@email.com', '1989-01-30', 'MASCULINO', 'EXTERNO', 'ACTIVO'),

-- LECTORES CON ESTADO SUSPENDIDO
-- Estudiantes suspendidos
('19191919', 'Andrea Carolina', 'Vásquez Herrera', 'Av. Angamos 456, Lima', '987456123', 'andrea.vasquez@email.com', '1999-04-12', 'FEMENINO', 'ESTUDIANTE', 'SUSPENDIDO'),
('20202020', 'Miguel Ángel', 'Peña Carrión', 'Jr. Callao 789, Lima', '976321654', 'miguel.pena@email.com', '1998-08-05', 'MASCULINO', 'ESTUDIANTE', 'SUSPENDIDO'),
('21212121', 'Daniela Esperanza', 'Campos Rojas', 'Av. San Luis 123, Lima', '965159753', 'daniela.campos@email.com', '2000-03-18', 'FEMENINO', 'ESTUDIANTE', 'SUSPENDIDO'),
('22212121', 'Franco Alexander', 'Inca Mamani', 'Jr. Huánuco 456, Lima', '954852741', 'franco.inca@email.com', '1997-11-28', 'MASCULINO', 'ESTUDIANTE', 'SUSPENDIDO'),

-- Docente suspendido
('23232323', 'Elena Victoria', 'Castillo Núñez', 'Av. Javier Prado 789, Lima', '943741852', 'elena.castillo@email.com', '1984-09-16', 'FEMENINO', 'DOCENTE', 'SUSPENDIDO'),

-- Externo suspendido
('24242424', 'Raúl Enrique', 'Pacheco Villar', 'Calle Bolívar 234, Lima', '932654987', 'raul.pacheco@email.com', '1991-05-23', 'MASCULINO', 'EXTERNO', 'SUSPENDIDO'),

-- LECTORES CON ESTADO BLOQUEADO
-- Estudiantes bloqueados
('25252525', 'Carla Beatriz', 'Salinas Montenegro', 'Av. Venezuela 345, Lima', '921753684', 'carla.salinas@email.com', '1998-12-09', 'FEMENINO', 'ESTUDIANTE', 'BLOQUEADO'),
('26262626', 'Omar Jesús', 'Chávez Cordero', 'Jr. Ancash 567, Lima', '910852963', 'omar.chavez@email.com', '1999-06-14', 'MASCULINO', 'ESTUDIANTE', 'BLOQUEADO'),
('27272727', 'Natalia Rocío', 'Bustamante Aguilar', 'Av. Pershing 890, Lima', '998765432', 'natalia.bustamante@email.com', '2001-02-27', 'FEMENINO', 'ESTUDIANTE', 'BLOQUEADO'),

-- Administrativo bloqueado
('28282828', 'Víctor Hugo', 'Delgado Ríos', 'Jr. Moquegua 123, Lima', '987321654', 'victor.delgado@email.com', '1986-07-11', 'MASCULINO', 'ADMINISTRATIVO', 'BLOQUEADO'),

-- Docente bloqueado
('29292929', 'Guadalupe Esperanza', 'Silva Paredes', 'Av. Benavides 456, Lima', '976654123', 'guadalupe.silva@email.com', '1981-10-04', 'FEMENINO', 'DOCENTE', 'BLOQUEADO'),

-- LECTORES CON ESTADO ELIMINADO
-- Estudiantes eliminados
('30303030', 'Ángel Gabriel', 'Romero Huanca', 'Av. República 789, Lima', '965987321', 'angel.romero@email.com', '1996-01-20', 'MASCULINO', 'ESTUDIANTE', 'ELIMINADO'),
('31313131', 'Melissa Fiorella', 'Cáceres Jiménez', 'Jr. Madre de Dios 234, Lima', '954123789', 'melissa.caceres@email.com', '1999-09-15', 'FEMENINO', 'ESTUDIANTE', 'ELIMINADO'),
('32323232', 'Erick Rolando', 'Huayhua Ccallo', 'Av. México 567, Lima', '943789456', 'erick.huayhua@email.com', '1998-05-03', 'MASCULINO', 'ESTUDIANTE', 'ELIMINADO'),

-- Docente eliminado
('33343433', 'Patricia Mercedes', 'Alarcón Vega', 'Jr. Amazonas 890, Lima', '932456789', 'patricia.alarcon@email.com', '1983-12-21', 'FEMENINO', 'DOCENTE', 'ELIMINADO'),

-- Administrativo eliminado
('34343434', 'Fernando José', 'Cárdenas Luna', 'Av. Faucett 123, Lima', '921654987', 'fernando.cardenas@email.com', '1988-03-17', 'MASCULINO', 'ADMINISTRATIVO', 'ELIMINADO'),

-- Externo eliminado
('35353535', 'Roxana Elizabeth', 'Mendoza Cruz', 'Calle Lima 456, Lima', '910321654', 'roxana.mendoza@email.com', '1993-08-08', 'FEMENINO', 'EXTERNO', 'ELIMINADO');

-- 17. EJEMPLARES
INSERT INTO copies (book_id, shelf_id, barcode, status, condition) VALUES 
-- La ciudad y los perros (5 copias)
(1, 1, '100001001', 'DISPONIBLE', 'BUENO'),
(1, 1, '100001002', 'DISPONIBLE', 'BUENO'),
(1, 1, '100001003', 'PRESTADO', 'REGULAR'),
(1, 1, '100001004', 'DISPONIBLE', 'NUEVO'),
(1, 1, '100001005', 'DISPONIBLE', 'BUENO'),

-- Cien años de soledad (4 copias)
(9, 2, '100009001', 'DISPONIBLE', 'BUENO'),
(9, 2, '100009002', 'DISPONIBLE', 'NUEVO'),
(9, 2, '100009003', 'RESERVADO', 'BUENO'),
(9, 2, '100009004', 'DISPONIBLE', 'MALO'),

-- Los ríos profundos (3 copias)
(2, 1, '100002001', 'DISPONIBLE', 'BUENO'),
(2, 1, '100002002', 'PRESTADO', 'BUENO'),
(2, 1, '100002003', 'DISPONIBLE', 'NUEVO'),

-- La palabra del mudo (2 copias)
(3, 1, '100003001', 'DISPONIBLE', 'REGULAR'),
(3, 1, '100003002', 'DISPONIBLE', 'BUENO'),

-- Trilce (2 copias)
(4, 1, '100004001', 'DISPONIBLE', 'BUENO'),
(4, 1, '100004002', 'EXTRAVIADO', 'REGULAR'),

-- Don Quijote (6 copias)
(17, 3, '100017001', 'DISPONIBLE', 'BUENO'),
(17, 3, '100017002', 'DISPONIBLE', 'REGULAR'),
(17, 3, '100017003', 'MANTENIMIENTO', 'BUENO'),
(17, 3, '100017004', 'DISPONIBLE', 'NUEVO'),
(17, 3, '100017005', 'DISPONIBLE', 'BUENO'),
(17, 3, '100017006', 'DISPONIBLE', 'REGULAR'),

-- Romeo y Julieta (3 copias)
(23, 4, '100023001', 'DISPONIBLE', 'BUENO'),
(23, 4, '100023002', 'DISPONIBLE', 'NUEVO'),
(23, 4, '100023003', 'PRESTADO', 'BUENO'),

-- El viejo y el mar (3 copias)
(24, 4, '100024001', 'DISPONIBLE', 'BUENO'),
(24, 4, '100024002', 'RESERVADO', 'BUENO'),
(24, 4, '100024003', 'DISPONIBLE', 'NUEVO'),

-- Harry Potter (6 copias)
(26, 19, '100026001', 'DISPONIBLE', 'NUEVO'),
(26, 19, '100026002', 'DISPONIBLE', 'BUENO'),
(26, 19, '100026003', 'PRESTADO', 'BUENO'),
(26, 19, '100026004', 'DISPONIBLE', 'BUENO'),
(26, 19, '100026005', 'DISPONIBLE', 'REGULAR'),
(26, 19, '100026006', 'DISPONIBLE', 'NUEVO'),

-- Asesinato en el Orient Express (2 copias)
(27, 4, '100027001', 'DISPONIBLE', 'BUENO'),
(27, 4, '100027002', 'EXTRAVIADO', 'REGULAR'),

-- Historia del Perú (4 copias)
(33, 7, '100033001', 'DISPONIBLE', 'NUEVO'),
(33, 7, '100033002', 'DISPONIBLE', 'BUENO'),
(33, 7, '100033003', 'MANTENIMIENTO', 'BUENO'),
(33, 7, '100033004', 'DISPONIBLE', 'DETERIORADO'),

-- Matemáticas (5 copias)
(34, 11, '100034001', 'DISPONIBLE', 'BUENO'),
(34, 11, '100034002', 'PRESTADO', 'BUENO'),
(34, 11, '100034003', 'DISPONIBLE', 'REGULAR'),
(34, 11, '100034004', 'DISPONIBLE', 'NUEVO'),
(34, 11, '100034005', 'DISPONIBLE', 'BUENO'),

-- Física (4 copias)
(35, 11, '100035001', 'DISPONIBLE', 'BUENO'),
(35, 11, '100035002', 'DISPONIBLE', 'NUEVO'),
(35, 11, '100035003', 'RESERVADO', 'REGULAR'),
(35, 11, '100035004', 'DISPONIBLE', 'BUENO'),

-- El Principito (8 copias)
(43, 19, '100043001', 'DISPONIBLE', 'BUENO'),
(43, 19, '100043002', 'DISPONIBLE', 'BUENO'),
(43, 19, '100043003', 'PRESTADO', 'REGULAR'),
(43, 19, '100043004', 'DISPONIBLE', 'NUEVO'),
(43, 19, '100043005', 'DISPONIBLE', 'BUENO'),
(43, 19, '100043006', 'DISPONIBLE', 'REGULAR'),
(43, 19, '100043007', 'MANTENIMIENTO', 'BUENO'),
(43, 19, '100043008', 'DISPONIBLE', 'NUEVO'),

-- Enciclopedia (3 copias)
(48, 23, '100048001', 'DISPONIBLE', 'NUEVO'),
(48, 23, '100048002', 'DISPONIBLE', 'BUENO'),
(48, 23, '100048003', 'DISPONIBLE', 'DETERIORADO'),

-- Ficciones de Borges (3 copias)
(10, 2, '100010001', 'DISPONIBLE', 'BUENO'),
(10, 2, '100010002', 'EXTRAVIADO', 'REGULAR'),
(10, 2, '100010003', 'DISPONIBLE', 'NUEVO'),

-- La casa de los espíritus (3 copias)
(11, 2, '100011001', 'DISPONIBLE', 'BUENO'),
(11, 2, '100011002', 'DISPONIBLE', 'NUEVO'),
(11, 2, '100011003', 'PRESTADO', 'BUENO'),

-- 1984 de Orwell (4 copias)
(28, 4, '100028001', 'DISPONIBLE', 'BUENO'),
(28, 4, '100028002', 'DISPONIBLE', 'NUEVO'),
(28, 4, '100028003', 'RESERVADO', 'REGULAR'),
(28, 4, '100028004', 'DISPONIBLE', 'BUENO'),

-- IT de Stephen King (2 copias)
(25, 4, '100025001', 'DISPONIBLE', 'BUENO'),
(25, 4, '100025002', 'MANTENIMIENTO', 'BUENO'),

-- Química Orgánica (3 copias)
(36, 12, '100036001', 'DISPONIBLE', 'NUEVO'),
(36, 12, '100036002', 'DISPONIBLE', 'BUENO'),
(36, 12, '100036003', 'PRESTADO', 'MALO'),

-- Anatomía Humana (2 copias)
(38, 14, '100038001', 'DISPONIBLE', 'NUEVO'),
(38, 14, '100038002', 'DISPONIBLE', 'BUENO'),

-- Matilda (4 copias)
(44, 19, '100044001', 'DISPONIBLE', 'BUENO'),
(44, 19, '100044002', 'DISPONIBLE', 'NUEVO'),
(44, 19, '100044003', 'EXTRAVIADO', 'REGULAR'),
(44, 19, '100044004', 'DISPONIBLE', 'BUENO'),

-- Rayuela (2 copias)
(13, 2, '100013001', 'DISPONIBLE', 'BUENO'),
(13, 2, '100013002', 'RESERVADO', 'REGULAR'),

-- Orgullo y prejuicio (3 copias)
(29, 4, '100029001', 'DISPONIBLE', 'BUENO'),
(29, 4, '100029002', 'DISPONIBLE', 'NUEVO'),
(29, 4, '100029003', 'DISPONIBLE', 'REGULAR'),

-- Tradiciones Peruanas (3 copias)
(5, 1, '100005001', 'DISPONIBLE', 'BUENO'),
(5, 1, '100005002', 'MANTENIMIENTO', 'REGULAR'),
(5, 1, '100005003', 'DISPONIBLE', 'NUEVO'),

-- Platero y yo (2 copias)
(20, 3, '100020001', 'DISPONIBLE', 'BUENO'),
(20, 3, '100020002', 'DISPONIBLE', 'REGULAR'),

-- Biología Molecular (2 copias)
(37, 12, '100037001', 'DISPONIBLE', 'NUEVO'),
(37, 12, '100037002', 'PRESTADO', 'BUENO'),

-- Psicología del Desarrollo (3 copias)
(41, 17, '100041001', 'DISPONIBLE', 'BUENO'),
(41, 17, '100041002', 'DISPONIBLE', 'NUEVO'),
(41, 17, '100041003', 'EXTRAVIADO', 'REGULAR'),

-- Diccionario RAE (2 copias)
(50, 24, '100050001', 'DISPONIBLE', 'NUEVO'),
(50, 24, '100050002', 'DISPONIBLE', 'BUENO');

-- 18. PRÉSTAMOS
INSERT INTO loans (reader_id, loan_date, observation) VALUES 
-- Julio 2024
(1, '2024-07-20', 'Préstamo regular'),
(2, '2024-07-21', 'Estudiante de literatura'), 
(3, '2024-07-22', 'Material para clase'),
(4, '2024-07-23', 'Préstamo administrativo'),
(5, '2024-07-24', 'Préstamo regular'),
(1, '2024-07-25', 'Segundo préstamo del mes'),
(6, '2024-07-26', 'Nuevo usuario'),
(7, '2024-07-27', 'Préstamo de fin de semana'),
(8, '2024-07-28', 'Material de investigación'),
(9, '2024-07-29', 'Préstamo regular'),
(10, '2024-07-30', 'Libros para proyecto'),

-- Agosto 2024
(11, '2024-08-01', 'Préstamo estudiantil'),
(12, '2024-08-02', 'Material académico'),
(13, '2024-08-03', 'Investigación literaria'),
(14, '2024-08-04', 'Préstamo regular'),
(15, '2024-08-05', 'Estudiante nuevo'),
(16, '2024-08-06', 'Material docente'),
(17, '2024-08-07', 'Préstamo administrativo'),
(18, '2024-08-08', 'Investigación histórica'),
(19, '2024-08-09', 'Préstamo externo'),
(2, '2024-08-10', 'Tercer préstamo'),
(20, '2024-08-11', 'Material técnico'),
(21, '2024-08-12', 'Préstamo docente'),
(22, '2024-08-13', 'Investigación científica'),
(23, '2024-08-14', 'Préstamo regular');

-- 19. ITEMS DE PRÉSTAMO
INSERT INTO loan_items (loan_id, copy_id, due_date, return_date, status) VALUES 
-- Préstamo 1 (Ana García) - MIXTO: 2 devueltos, 1 retrasado
(1, 3, '2024-08-03', '2024-08-01', 'DEVUELTO'),
(1, 25, '2024-08-03', '2024-08-01', 'DEVUELTO'),
(1, 47, '2024-08-03', NULL, 'RETRASADO'),

-- Préstamo 2 (Carlos Mendoza) - MIXTO: 1 prestado, 1 retrasado
(2, 13, '2024-08-04', NULL, 'PRESTADO'),
(2, 29, '2024-08-04', NULL, 'RETRASADO'),

-- Préstamo 3 (María Rodríguez) - AMBOS retrasados (mantener igual)
(3, 35, '2024-08-05', NULL, 'RETRASADO'),
(3, 52, '2024-08-05', NULL, 'RETRASADO'),

-- Préstamo 4 (José Fernández) - ÚNICO devuelto (mantener igual)
(4, 10, '2024-08-06', '2024-08-04', 'DEVUELTO'),

-- Préstamo 5 (Patricia Vargas) - MIXTO: 1 prestado, 1 retrasado, 1 devuelto
(5, 59, '2024-08-07', '2024-08-06', 'DEVUELTO'),
(5, 22, '2024-08-07', NULL, 'PRESTADO'),
(5, 36, '2024-08-07', NULL, 'RETRASADO'),

-- Préstamo 6 (Ana García - segundo) - ÚNICO retrasado (mantener igual)
(6, 32, '2024-08-08', NULL, 'RETRASADO'),

-- Préstamo 7 (Roberto Castro) - MIXTO: 1 devuelto, 1 prestado
(7, 51, '2024-08-09', '2024-08-07', 'DEVUELTO'),
(7, 34, '2024-08-09', NULL, 'PRESTADO'),

-- Préstamo 8 (Sofía Quispe) - ÚNICO extraviado (mantener igual)
(8, 4, '2024-08-10', NULL, 'EXTRAVIADO'),

-- Préstamo 9 (Javier Huamán) - MIXTO: 1 retrasado, 1 devuelto, 1 cancelado
(9, 18, '2024-08-11', NULL, 'RETRASADO'),
(9, 44, '2024-08-11', '2024-08-10', 'DEVUELTO'),
(9, 58, '2024-08-11', NULL, 'CANCELADO'),

-- Préstamo 10 (Camila Torres) - MIXTO: 1 devuelto, 1 retrasado
(10, 14, '2024-08-12', '2024-08-10', 'DEVUELTO'),
(10, 38, '2024-08-12', NULL, 'RETRASADO'),

-- Préstamo 11 (Mateo Condori) - ÚNICO prestado (mantener igual)
(11, 55, '2024-08-14', NULL, 'PRESTADO'),

-- Préstamo 12 (Valeria Ramos) - MIXTO: 1 retrasado, 1 extraviado
(12, 7, '2024-08-15', NULL, 'RETRASADO'),
(12, 42, '2024-08-15', NULL, 'EXTRAVIADO'),

-- Préstamo 13 (Sebastián Poma) - MIXTO: 1 devuelto, 1 prestado
(13, 19, '2024-08-16', '2024-08-14', 'DEVUELTO'),
(13, 26, '2024-08-16', NULL, 'PRESTADO'),

-- Préstamo 14 (Isabella Nina) - ÚNICO extraviado (mantener igual)
(14, 41, '2024-08-17', NULL, 'EXTRAVIADO'),

-- Préstamo 15 (Adrián Choque) - MIXTO: 1 prestado, 1 cancelado
(15, 57, '2024-08-18', NULL, 'PRESTADO'),
(15, 49, '2024-08-18', NULL, 'CANCELADO'),

-- Préstamo 16 (Carmen Herrera - docente) - ÚNICO retrasado (mantener igual)
(16, 15, '2024-08-19', NULL, 'RETRASADO'),

-- Préstamo 17 (Sandra López - administrativa) - MIXTO: 2 devueltos, 1 prestado
(17, 20, '2024-08-20', '2024-08-18', 'DEVUELTO'),
(17, 45, '2024-08-20', '2024-08-18', 'DEVUELTO'),
(17, 61, '2024-08-20', NULL, 'PRESTADO'),

-- Préstamo 18 (Luis Vega - docente) - MIXTO: 1 prestado, 1 retrasado
(18, 33, '2024-08-21', NULL, 'PRESTADO'),
(18, 56, '2024-08-21', NULL, 'RETRASADO'),

-- Préstamo 19 (Pilar Alvarez - externa) - ÚNICO cancelado (mantener igual)
(19, 8, '2024-08-22', NULL, 'CANCELADO'),

-- Préstamo 20 (Carlos Mendoza - segundo) - MIXTO: 1 retrasado, 1 extraviado
(20, 12, '2024-08-23', NULL, 'RETRASADO'),
(20, 46, '2024-08-23', NULL, 'EXTRAVIADO'),

-- Préstamo 21 (Eduardo Espinoza - externo) - ÚNICO devuelto (mantener igual)
(21, 24, '2024-08-24', '2024-08-22', 'DEVUELTO'),

-- Préstamo 22 (Rosa Guerrero - docente) - ÚNICO extraviado (mantener igual)
(22, 48, '2024-08-25', NULL, 'EXTRAVIADO'),

-- Préstamo 23 (Jorge Medina - docente) - MIXTO: 1 prestado, 1 devuelto, 1 retrasado
(23, 9, '2024-08-26', NULL, 'PRESTADO'),
(23, 27, '2024-08-26', '2024-08-24', 'DEVUELTO'),
(23, 53, '2024-08-26', NULL, 'RETRASADO'),

-- Préstamo 24 (Ricardo Morales - administrativo) - MIXTO: 1 retrasado, 1 cancelado
(24, 16, '2024-08-27', NULL, 'RETRASADO'),
(24, 39, '2024-08-27', NULL, 'CANCELADO'),

-- Préstamo 25 (Fernanda Mamani) - ÚNICO devuelto (mantener igual)
(25, 60, '2024-08-28', '2024-08-26', 'DEVUELTO');

-- 20. RESERVAS
INSERT INTO reservations (reader_id, copy_id, reservation_date, status) VALUES 
-- PENDIENTES (8 reservas)
(1, 50, '2024-07-28', 'PENDIENTE'),      -- Ana espera Diccionario RAE
(2, 31, '2024-07-29', 'PENDIENTE'),      -- Carlos espera Las aventuras de Tom Sawyer
(5, 17, '2024-07-30', 'PENDIENTE'),      -- Patricia espera Don Quijote
(8, 37, '2024-08-01', 'PENDIENTE'),      -- Sofía espera Biología Molecular
(11, 21, '2024-08-02', 'PENDIENTE'),     -- Mateo espera La colmena
(14, 43, '2024-08-03', 'PENDIENTE'),     -- Isabella espera El Principito
(17, 11, '2024-08-04', 'PENDIENTE'),     -- Sandra espera La casa de los espíritus
(20, 28, '2024-08-05', 'PENDIENTE'),     -- Eduardo espera 1984

-- ATENDIDAS (12 reservas) 
(3, 35, '2024-07-25', 'ATENDIDA'),       -- María retiró Física
(3, 52, '2024-07-25', 'ATENDIDA'),       -- María retiró segundo libro
(6, 32, '2024-07-26', 'ATENDIDA'),       -- Roberto retiró El cuervo
(9, 18, '2024-07-27', 'ATENDIDA'),       -- Javier retiró Romancero Gitano
(9, 44, '2024-07-27', 'ATENDIDA'),       -- Javier retiró Matilda
(9, 58, '2024-07-27', 'ATENDIDA'),       -- Javier retiró tercer libro
(12, 7, '2024-07-28', 'ATENDIDA'),       -- Valeria retiró Don Quijote
(12, 42, '2024-07-28', 'ATENDIDA'),       -- Valeria retiró segundo libro
(16, 15, '2024-07-29', 'ATENDIDA'),       -- Carmen retiró La muerte de Artemio Cruz
(20, 12, '2024-07-30', 'ATENDIDA'),       -- Eduardo retiró La casa de los espíritus
(20, 46, '2024-07-30', 'ATENDIDA'),       -- Eduardo retiró segundo libro
(24, 16, '2024-08-01', 'ATENDIDA'),       -- Ricardo retiró El túnel

-- CANCELADAS Y EXPIRADAS (5 reservas)
(4, 40, '2024-07-20', 'EXPIRADA'),        -- José - reserva expirada de Principios de Economía
(7, 30, '2024-07-21', 'CANCELADA'),       -- Roberto canceló Oliver Twist  
(10, 54, '2024-07-22', 'EXPIRADA'),       -- Camila - reserva expirada
(15, 23, '2024-07-23', 'CANCELADA'),      -- Adrián canceló Romeo y Julieta
(19, 6, '2024-07-24', 'EXPIRADA');        -- Pilar - reserva expirada de El mundo es ancho y ajeno

-- 21. MULTAS (25 multas con tres estados)
INSERT INTO fines (loan_id, copy_id, amount, days_late, status, issued_at) VALUES 
-- MULTAS PENDIENTES (8 multas)
(3, 35, '7.50', 3, 'PENDIENTE', '2024-08-08'),     -- María debe por Física
(6, 32, '5.00', 2, 'PENDIENTE', '2024-08-10'),     -- Ana debe por El cuervo
(9, 18, '10.00', 4, 'PENDIENTE', '2024-08-15'),    -- Javier debe por Romancero Gitano
(9, 44, '10.00', 4, 'PENDIENTE', '2024-08-15'),    -- Javier debe por Matilda
(12, 7, '12.50', 5, 'PENDIENTE', '2024-08-20'),    -- Valeria debe por Don Quijote
(16, 15, '6.25', 2, 'PENDIENTE', '2024-08-21'),    -- Carmen debe por La muerte de Artemio Cruz
(20, 12, '8.75', 3, 'PENDIENTE', '2024-08-26'),    -- Eduardo debe por La casa de los espíritus
(24, 16, '11.25', 4, 'PENDIENTE', '2024-08-31'),   -- Ricardo debe por El túnel

-- MULTAS CONDONADAS (4 multas)
(3, 52, '7.50', 3, 'CONDONADO', '2024-08-08'),     -- María - multa condonada por segundo libro
(9, 58, '10.00', 4, 'CONDONADO', '2024-08-15'),    -- Javier - multa condonada por tercer libro
(12, 42, '12.50', 5, 'CONDONADO', '2024-08-20'),   -- Valeria - multa condonada por segundo libro
(20, 46, '8.75', 3, 'CONDONADO', '2024-08-26'),    -- Eduardo - multa condonada por segundo libro

-- MULTAS PAGADAS (13 multas)
(1, 3, '2.50', 1, 'PAGADO', '2024-07-25'),         -- Ana pagó por La ciudad y los perros
(1, 25, '2.50', 1, 'PAGADO', '2024-07-25'),        -- Ana pagó por IT
(1, 47, '2.50', 1, 'PAGADO', '2024-07-25'),        -- Ana pagó por El Principito
(4, 10, '3.75', 1, 'PAGADO', '2024-08-05'),        -- José pagó por Ficciones
(7, 51, '5.00', 2, 'PAGADO', '2024-08-09'),        -- Roberto pagó por Matilda
(7, 34, '5.00', 2, 'PAGADO', '2024-08-09'),        -- Roberto pagó por Matemáticas
(8, 4, '15.00', 6, 'PAGADO', '2024-08-16'),        -- Sofía pagó por Trilce (extraviado)
(13, 19, '3.75', 1, 'PAGADO', '2024-08-17'),       -- Sebastián pagó por Campos de Castilla
(13, 26, '3.75', 1, 'PAGADO', '2024-08-17'),       -- Sebastián pagó por Harry Potter
(17, 20, '2.50', 1, 'PAGADO', '2024-08-21'),       -- Sandra pagó por Platero y yo
(17, 45, '2.50', 1, 'PAGADO', '2024-08-21'),       -- Sandra pagó por Charlie y la fábrica
(17, 61, '2.50', 1, 'PAGADO', '2024-08-21'),       -- Sandra pagó por Psicología
(21, 24, '3.75', 1, 'PAGADO', '2024-08-25');       -- Eduardo pagó por El viejo y el mar

-- 22. PAGOS
INSERT INTO payments (reader_id, amount, payment_date, method) VALUES 
-- EFECTIVO (6 pagos)
(1, '7.50', '2024-07-26', 'EFECTIVO'),       -- Ana pagó 3 multas de $2.50 c/u
(4, '3.75', '2024-08-06', 'EFECTIVO'),       -- José pagó 1 multa
(17, '7.50', '2024-08-22', 'EFECTIVO'),      -- Sandra pagó 3 multas de $2.50 c/u
(21, '3.75', '2024-08-26', 'EFECTIVO'),      -- Eduardo pagó 1 multa
(2, '15.00', '2024-08-28', 'EFECTIVO'),      -- Carlos pago múltiple
(5, '12.00', '2024-08-29', 'EFECTIVO'),      -- Patricia pago múltiple

-- TARJETA (5 pagos)
(7, '10.00', '2024-08-10', 'TARJETA'),       -- Roberto pagó 2 multas de $5.00 c/u
(8, '15.00', '2024-08-17', 'TARJETA'),       -- Sofía pagó 1 multa de extravío
(13, '7.50', '2024-08-18', 'TARJETA'),       -- Sebastián pagó 2 multas de $3.75 c/u
(11, '18.00', '2024-08-30', 'TARJETA'),      -- Mateo pago múltiple
(15, '20.00', '2024-09-01', 'TARJETA'),      -- Adrián pago múltiple

-- TRANSFERENCIA (4 pagos)
(3, '15.00', '2024-08-12', 'TRANSFERENCIA'), -- María pagará 2 multas de $7.50 c/u
(12, '25.00', '2024-08-25', 'TRANSFERENCIA'), -- Valeria pagará 2 multas de $12.50 c/u
(16, '6.25', '2024-08-22', 'TRANSFERENCIA'), -- Carmen pagará 1 multa
(18, '22.00', '2024-09-02', 'TRANSFERENCIA'), -- Luis pago múltiple

-- CHEQUE (4 pagos)
(9, '30.00', '2024-08-20', 'CHEQUE'),        -- Javier pagará 3 multas de $10.00 c/u
(20, '17.50', '2024-08-30', 'CHEQUE'),       -- Eduardo pagará 2 multas de $8.75 c/u
(6, '14.00', '2024-09-03', 'CHEQUE'),        -- Roberto pago múltiple
(10, '16.50', '2024-09-04', 'CHEQUE'),       -- Camila pago múltiple

-- OTROS (3 pagos)
(14, '25.00', '2024-08-31', 'OTROS'),        -- Isabella pago múltiple
(22, '11.25', '2024-09-05', 'OTROS'),        -- Rosa pago múltiple
(24, '13.75', '2024-09-06', 'OTROS');        -- Ricardo pago múltiple

-- 23. RELACIÓN PAGOS-MULTAS 
INSERT INTO payment_fines (payment_id, fine_id) VALUES 
(1, 13), (1, 14), (1, 15),    -- Ana pagó 3 multas con 1 pago
(2, 16),                       -- José pagó 1 multa
(3, 17), (3, 18),             -- Roberto pagó 2 multas con 1 pago  
(4, 19),                       -- Sofía pagó 1 multa (extravío)
(5, 20), (5, 21),             -- Sebastián pagó 2 multas con 1 pago
(6, 22), (6, 23), (6, 24),    -- Sandra pagó 3 multas con 1 pago
(7, 25),                       -- Eduardo pagó 1 multa
(8, 1), (8, 2),               -- María pagará 2 multas con 1 pago
(9, 4), (9, 5), (9, 6),       -- Javier pagará 3 multas con 1 pago
(10, 7), (10, 8),             -- Valeria pagará 2 multas con 1 pago
(11, 9),                       -- Carmen pagará 1 multa
(12, 10), (12, 11),           -- Eduardo pagará 2 multas con 1 pago
(13, 12),                      -- Ricardo pagará 1 multa
(14, 3),    -- Carlos (payment_id=14) -> multa pendiente de María (fine_id=3)
(15, 1),    -- Patricia (payment_id=15) -> multa pendiente de María (fine_id=1) 
(16, 2),    -- Mateo (payment_id=16) -> multa pendiente de Ana (fine_id=2)
(17, 4),    -- Adrián (payment_id=17) -> multa pendiente de Javier (fine_id=4)
(18, 5),    -- Luis (payment_id=18) -> multa pendiente de Javier (fine_id=5)
(19, 6),    -- Isabella (payment_id=19) -> multa pendiente de Javier (fine_id=6)
(20, 7),    -- Rosa (payment_id=20) -> multa pendiente de Valeria (fine_id=7)
(21, 8),    -- Roberto (payment_id=21) -> multa pendiente de Valeria (fine_id=8)
(22, 9);    -- Camila (payment_id=22) -> multa pendiente de Carmen (fine_id=9)

COMMIT;