INSERT INTO comunidades (nombre, direccion, ciudad, cod_postal) VALUES
('Administración', 'Administración', 'Cádiz', '11100'),
('pruebas-test', 'pruebas', 'Cádiz', '11100');

INSERT INTO usuarios (dni, nombre, apellidos, puerta, telefono, password, cambiar_pass, rol, id_comunidad, email, coeficiente, estado) VALUES
('12345888A', 'Super', 'Admin', '1A', '600111222', '$2a$10$o51IvtAMVqeGUOtAqKPSMOZR1fml/QECe46WpCgqSM0ytXn2buePO', FALSE, 'SUPER_ADMIN', 1, 'super@admin.com', 5.50, TRUE),
('22345888A', 'Admin', 'Admin', '1A', '600111222', '$2a$10$o51IvtAMVqeGUOtAqKPSMOZR1fml/QECe46WpCgqSM0ytXn2buePO', FALSE, 'ADMIN', 2, 'admin@admin.com', 5.50, TRUE),
('32345888A', 'User', 'User', '2A', '600111222', '$2a$10$o51IvtAMVqeGUOtAqKPSMOZR1fml/QECe46WpCgqSM0ytXn2buePO', FALSE, 'USER', 2, 'user@user.com', 5.50, TRUE);
