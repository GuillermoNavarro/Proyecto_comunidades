CREATE TABLE IF NOT EXISTS comunidades (
	id_comunidad BIGINT NOT NULL AUTO_INCREMENT,
	nombre varchar (100) NOT NULL,
	direccion varchar (150) NOT NULL,
	ciudad varchar (50) NOT NULL,
	cod_postal varchar (10) NOT NULL,
	CONSTRAINT pk_comunidades PRIMARY KEY (id_comunidad)
);

CREATE TABLE IF NOT EXISTS usuarios(
	id_usuario BIGINT NOT NULL AUTO_INCREMENT,
    	dni varchar(9) NOT NULL UNIQUE,
	nombre varchar (50) NOT NULL,
	apellidos varchar (100) NOT NULL,
	puerta varchar (20) NOT NULL,
	telefono varchar (24) NOT NULL,
	password varchar (255) NOT NULL,
	cambiar_pass boolean NOT NULL DEFAULT TRUE,
	rol varchar (20) NOT NULL,
	id_comunidad BIGINT NOT NULL,
	email varchar (100) NOT NULL UNIQUE,
    	coeficiente decimal (5, 2) NOT NULL DEFAULT 0.00,
    	estado boolean NOT NULL DEFAULT TRUE,
	CONSTRAINT pk_usuarios PRIMARY KEY (id_usuario)
);

CREATE TABLE IF NOT EXISTS movimientos(
	id_movimiento BIGINT NOT NULL AUTO_INCREMENT,
	nombre varchar (100) NOT NULL,
	id_comunidad BIGINT NOT NULL,
	id_usuario BIGINT NULL,
	fecha date NOT NULL,
	importe decimal (10, 2) NOT NULL,
	tipo varchar (20) NOT NULL,
    	id_recibo BIGINT NULL,
	CONSTRAINT pk_movimientos PRIMARY KEY (id_movimiento)
);

CREATE TABLE IF NOT EXISTS cuotas(
 	id_cuota BIGINT NOT NULL AUTO_INCREMENT,
    	nombre varchar (100) NOT NULL,
    	fecha_emision date NOT NULL,
	fecha_vencimiento date NOT NULL,
 	importe_total decimal (10,2) NOT NULL,
 	tipo varchar (20) NOT NULL,
    	id_comunidad BIGINT NOT NULL,
    	CONSTRAINT pk_cuotas PRIMARY KEY (id_cuota)
);

CREATE TABLE IF NOT EXISTS publicaciones(
	id_publicacion BIGINT NOT NULL AUTO_INCREMENT,
	tipo varchar (20) NOT NULL,
	titulo varchar (200) NOT NULL,
	descripcion varchar (800) NOT NULL,
	imagen varchar(100) NULL,
	id_usuario BIGINT NOT NULL,
	id_comunidad BIGINT NOT NULL,
	fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_completado DATETIME NULL,
	estado boolean NOT NULL DEFAULT TRUE,
	moderado boolean NOT NULL DEFAULT FALSE,
	id_documento BIGINT NULL,
	CONSTRAINT pk_publicaciones PRIMARY KEY (id_publicacion)
);

CREATE TABLE IF NOT EXISTS comentarios(
	id_comentario BIGINT NOT NULL AUTO_INCREMENT,
	id_publicacion BIGINT NOT NULL,
	id_usuario BIGINT NOT NULL,
	texto varchar (500) NOT NULL,
	fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	imagen varchar (100) NULL,
	moderado boolean NOT NULL DEFAULT FALSE,
	CONSTRAINT pk_comentarios PRIMARY KEY (id_comentario)
);

CREATE TABLE IF NOT EXISTS documentos(
	id_documento BIGINT NOT NULL AUTO_INCREMENT,
	id_comunidad BIGINT NOT NULL,
	nombre varchar (100) NOT NULL,
	descripcion varchar (500) NULL,
	fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	documento varchar (100) NOT NULL,
	CONSTRAINT pk_documentos PRIMARY KEY (id_documento)
);

CREATE TABLE IF NOT EXISTS recibos(
	id_recibo BIGINT NOT NULL AUTO_INCREMENT,
	id_cuota BIGINT NOT NULL,
	id_comunidad BIGINT NOT NULL,
 	id_usuario BIGINT NOT NULL,
	importe decimal (10, 2) NOT NULL,
	estado varchar (20) NOT NULL,
	CONSTRAINT pk_recibos PRIMARY KEY (id_recibo) 
);



ALTER TABLE usuarios ADD CONSTRAINT fk_usuarios_comunidades FOREIGN KEY (id_comunidad) REFERENCES comunidades (id_comunidad) ON DELETE CASCADE;
ALTER TABLE movimientos ADD CONSTRAINT fk_movimientos_comunidades  FOREIGN KEY (id_comunidad) REFERENCES comunidades (id_comunidad) ON DELETE CASCADE;
ALTER TABLE movimientos ADD CONSTRAINT fk_movimientos_usuarios FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)ON DELETE CASCADE;
ALTER TABLE cuotas ADD CONSTRAINT fk_cuotas_comunidades FOREIGN KEY (id_comunidad) REFERENCES comunidades (id_comunidad) ON DELETE CASCADE;
ALTER TABLE movimientos ADD CONSTRAINT fk_movimientos_recibos FOREIGN KEY (id_recibo) REFERENCES recibos (id_recibo) ON DELETE CASCADE;
ALTER TABLE publicaciones ADD CONSTRAINT fk_publicaciones_comunidades  FOREIGN KEY (id_comunidad) REFERENCES comunidades (id_comunidad) ON DELETE CASCADE;
ALTER TABLE publicaciones ADD CONSTRAINT fk_publicaciones_usuarios FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE;
ALTER TABLE publicaciones ADD CONSTRAINT fk_publicaciones_documentos  FOREIGN KEY (id_documento) REFERENCES documentos (id_documento) ON DELETE CASCADE;
ALTER TABLE comentarios ADD CONSTRAINT fk_comentarios_publicaciones FOREIGN KEY (id_publicacion) REFERENCES publicaciones (id_publicacion) ON DELETE CASCADE;
ALTER TABLE comentarios ADD CONSTRAINT fk_comentarios_usuarios FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE;
ALTER TABLE documentos ADD CONSTRAINT fk_documentos_comunidades FOREIGN KEY (id_comunidad) REFERENCES comunidades (id_comunidad) ON DELETE CASCADE;
ALTER TABLE recibos ADD CONSTRAINT fk_recibos_comunidades FOREIGN KEY (id_comunidad) REFERENCES comunidades (id_comunidad) ON DELETE CASCADE;
ALTER TABLE recibos ADD CONSTRAINT fk_recibos_usuarios  FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE;
ALTER TABLE recibos ADD CONSTRAINT fk_recibos_cuotas FOREIGN KEY (id_cuota) REFERENCES cuotas (id_cuota) ON DELETE CASCADE;