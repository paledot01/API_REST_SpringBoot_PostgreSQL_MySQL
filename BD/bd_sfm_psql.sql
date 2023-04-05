-- FALSE = INACTIVO: 0
-- TRUE = ACTIVO: 1

CREATE TABLE IF NOT EXISTS estado(
	cod_estado         CHAR(3) PRIMARY KEY,
	nombre_estado      VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS distrito(
	cod_distrito       CHAR(4) PRIMARY KEY,
	nombre_distrito    VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS moneda(
	cod_moneda         CHAR(4) PRIMARY KEY,
	nombre_moneda      VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS talla(
	cod_talla          CHAR(4) PRIMARY KEY,
	numero_talla       DECIMAL NOT NULL UNIQUE,
	estado             SMALLINT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS color(
	cod_color          CHAR(4) PRIMARY KEY,
	nombre_color       VARCHAR(25) NOT NULL UNIQUE,
	estado             SMALLINT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS categoria(
	cod_categoria      CHAR(4) PRIMARY KEY,
	nombre_categoria   VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS estilo(
	cod_estilo         CHAR(4) PRIMARY KEY,
	nombre_estilo      VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS rol(
	cod_rol            CHAR(4) PRIMARY KEY,
	nombre_rol         VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS empleado( -- DROP TABLE IF EXISTS empleado;
	cod_empleado       CHAR(7) PRIMARY KEY,
	cod_distrito       CHAR(4),
	cod_estado         CHAR(3),
	nombre             VARCHAR(25) NOT NULL,-- CHECK (nombre != "") ,
	apellidos          VARCHAR(25) NOT NULL,
	dni                CHAR(8) UNIQUE,
	direccion          VARCHAR(45),
	telefono           VARCHAR(12),
	email              VARCHAR(45) UNIQUE,
	usuario            VARCHAR(25) NOT NULL UNIQUE,
	contrasena         VARCHAR(100) NOT NULL,
	FOREIGN KEY (cod_distrito) REFERENCES distrito(cod_distrito) ON DELETE CASCADE,
	FOREIGN KEY (cod_estado) REFERENCES estado(cod_estado) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS empleado_rol( -- DROP TABLE IF EXISTS empleado_rol;
	cod_rol            CHAR(4),
	cod_empleado       CHAR(7),
	PRIMARY KEY (cod_rol, cod_empleado),
	FOREIGN KEY (cod_rol) REFERENCES rol(cod_rol) ON DELETE CASCADE,
	FOREIGN KEY (cod_empleado) REFERENCES empleado(cod_empleado) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS marca(
	cod_marca          CHAR(7) PRIMARY KEY,
	nombre_marca       VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS modelo( -- DROP TABLE IF EXISTS modelo;
	cod_modelo         CHAR(7) PRIMARY KEY,
	cod_marca          CHAR(7),
	cod_categoria      CHAR(4),
	cod_estilo         CHAR(4),
	nombre_modelo      VARCHAR(25) NOT NULL UNIQUE,
	precio_compra      DECIMAL NOT NULL,
	precio_venta       DECIMAL NOT NULL, -- precio final
	precio_lista       DECIMAL NOT NULL, -- precio inicial
	descuento_modelo   INT DEFAULT 0 CHECK(descuento_modelo>=0 AND descuento_modelo<=100),
	descripcion        VARCHAR(250) DEFAULT 'descrixion generica',
	stock_min          INT DEFAULT 0,
	FOREIGN KEY (cod_marca) REFERENCES marca(cod_marca) ON DELETE CASCADE,
	FOREIGN KEY (cod_categoria) REFERENCES categoria(cod_categoria) ON DELETE CASCADE,
	FOREIGN KEY (cod_estilo) REFERENCES estilo(cod_estilo) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS modelo_color( -- DROP TABLE IF EXISTS modelo_color;
	--cod_mc            CHAR(7),
	cod_modelo         CHAR(7),
	cod_color          CHAR(4),
	imagen_url         VARCHAR(200),
	PRIMARY KEY (cod_modelo, cod_color),
	FOREIGN KEY (cod_modelo) REFERENCES modelo(cod_modelo) ON DELETE CASCADE,
	FOREIGN KEY (cod_color) REFERENCES color(cod_color) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS calzado( -- DROP TABLE IF EXISTS calzado;
	cod_calzado        CHAR(8) UNIQUE, -- *****************
	cod_modelo         CHAR(7),
	cod_color          CHAR(4),
	cod_talla          CHAR(4),
	codigo_br          VARCHAR(20) UNIQUE,
	stock              INT DEFAULT 1 CHECK(stock>=0),
	PRIMARY KEY (cod_calzado,cod_modelo,cod_color,cod_talla),
	CONSTRAINT fk_calzado1 FOREIGN KEY(cod_modelo,cod_color) REFERENCES modelo_color(cod_modelo,cod_color) ON DELETE CASCADE,
	FOREIGN KEY (cod_talla) REFERENCES talla(cod_talla) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cliente( -- DROP TABLE IF EXISTS cliente;
	cod_cliente        CHAR(8) PRIMARY KEY,
	cod_distrito       CHAR(4),
	nombre             VARCHAR(25) NOT NULL,
	apellidos          VARCHAR(25) NOT NULL,
	dni                CHAR(8) NOT NULL UNIQUE,
	direccion          VARCHAR(45),
	telefono           VARCHAR(12),
	email              VARCHAR(45) UNIQUE,
	FOREIGN KEY (cod_distrito) REFERENCES distrito(cod_distrito) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS boleta( -- DROP TABLE IF EXISTS boleta;
	cod_boleta         CHAR(8) PRIMARY KEY,
	cod_cliente        CHAR(8),
	cod_empleado       CHAR(7),
	cod_moneda         CHAR(4),
	fecha_hora_emision TIMESTAMP NOT NULL,
	importe_total      DECIMAL NOT NULL,
	FOREIGN KEY (cod_cliente) REFERENCES cliente(cod_cliente) ON DELETE CASCADE,
	FOREIGN KEY (cod_empleado) REFERENCES empleado(cod_empleado) ON DELETE CASCADE,
	FOREIGN KEY (cod_moneda) REFERENCES moneda(cod_moneda) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS detalle_boleta( -- DROP TABLE IF EXISTS detalle_boleta;
	cod_boleta         CHAR(8),
	cod_calzado        CHAR(8),
	cantidad           INT NOT NULL,
	-- importe bruto   -> cantidad * [p_venta(P.U) - igv]
	igv                DECIMAL DEFAULT 0.18,
	descuento          DECIMAL DEFAULT 0,
	importe            DECIMAL NOT NULL,
	PRIMARY KEY (cod_boleta, cod_calzado),
	FOREIGN KEY (cod_boleta) REFERENCES boleta(cod_boleta) ON DELETE CASCADE,
	FOREIGN KEY (cod_calzado) REFERENCES calzado(cod_calzado) ON DELETE CASCADE
);

-- ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
-- ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

-- ESTADO -> SELECT * FROM estado;
INSERT INTO estado (cod_estado, nombre_estado) VALUES ('ES1','INACTIVO');
INSERT INTO estado (cod_estado, nombre_estado) VALUES ('ES2','ACTIVO');
INSERT INTO estado (cod_estado, nombre_estado) VALUES ('ES3','SUSPENDIDO');

-- DISTRITO -> SELECT * FROM distrito;
INSERT INTO distrito (cod_distrito, nombre_distrito) VALUES ('DI01','Comas');
INSERT INTO distrito (cod_distrito, nombre_distrito) VALUES ('DI02','Lince');
INSERT INTO distrito (cod_distrito, nombre_distrito) VALUES ('DI03','Miraflores');
INSERT INTO distrito (cod_distrito, nombre_distrito) VALUES ('DI04','Los Olivos');
INSERT INTO distrito (cod_distrito, nombre_distrito) VALUES ('DI05','S.J.L');
INSERT INTO distrito (cod_distrito, nombre_distrito) VALUES ('DI06','Independencia');

-- MONEDA -> SELECT * FROM moneda;
INSERT INTO moneda (cod_moneda, nombre_moneda) VALUES ('MN01','SOL');
INSERT INTO moneda (cod_moneda, nombre_moneda) VALUES ('MN02','DOLAR');

-- TALLA -> SELECT * FROM talla;
INSERT INTO talla (cod_talla, numero_talla) VALUES ('TL01',38);
INSERT INTO talla (cod_talla, numero_talla) VALUES ('TL02',39);
INSERT INTO talla (cod_talla, numero_talla) VALUES ('TL03',40);
INSERT INTO talla (cod_talla, numero_talla) VALUES ('TL04',41);
INSERT INTO talla (cod_talla, numero_talla) VALUES ('TL05',41.5);
INSERT INTO talla (cod_talla, numero_talla) VALUES ('TL06',42);

-- COLOR -> SELECT * FROM color;
INSERT INTO color (cod_color, nombre_color) VALUES ('CR01','Negro');
INSERT INTO color (cod_color, nombre_color) VALUES ('CR02','Marron');
INSERT INTO color (cod_color, nombre_color) VALUES ('CR03','Azul');
INSERT INTO color (cod_color, nombre_color) VALUES ('CR04','Canela');
INSERT INTO color (cod_color, nombre_color) VALUES ('CR05','Azul Marino');

-- CATEGORIA -> SELECT * FROM categoria;
INSERT INTO categoria (cod_categoria, nombre_categoria) VALUES ('CT01','Mocasin');
INSERT INTO categoria (cod_categoria, nombre_categoria) VALUES ('CT02','Botin');

-- ESTILO -> SELECT * FROM estilo;
INSERT INTO estilo (cod_estilo, nombre_estilo) VALUES ('ET01','Casual');
INSERT INTO estilo (cod_estilo, nombre_estilo) VALUES ('ET02','Vestir');

-- ROL -> SELECT * FROM rol;
INSERT INTO rol (cod_rol, nombre_rol) VALUES ('RL01','ROLE_ADMIN');
INSERT INTO rol (cod_rol, nombre_rol) VALUES ('RL02','ROLE_USER');

-- EMPLEADO -> SELECT * FROM empleado;
INSERT INTO empleado (cod_empleado,cod_distrito,cod_estado,nombre,apellidos,dni,direccion,telefono,email,usuario,contrasena) VALUES ('EM10001','DI01','ES2','KEVIN','B','00000000','DIRECCION','999999999','paledot01@gmail.com','kevinB','$2a$10$Jtfxa0EuEjZrfQ4OvR4WbuqD00OBIfIp.5Sv33A7G8ya3xTI542nq');

-- EMPLEADO-ROL -> SELECT * FROM empleado_rol;
INSERT INTO empleado_rol (cod_rol,cod_empleado) VALUES ('RL01','EM10001');
INSERT INTO empleado_rol (cod_rol,cod_empleado) VALUES ('RL02','EM10001');

-- MARCA -> SELECT * FROM marca;  --> DELETE FROM marca WHERE cod_marca = 'MA10002';  --> UPDATE marca SET field='C', field2='Z' WHERE id=3;
INSERT INTO marca (cod_marca, nombre_marca) VALUES ('MA10001','Calimod');
INSERT INTO marca (cod_marca, nombre_marca) VALUES ('MA10002','Basement');
INSERT INTO marca (cod_marca, nombre_marca) VALUES ('MA10003','Call It Spring');

-- MODELO -> SELECT * FROM modelo; --> DELETE FROM modelo WHERE cod_modelo = 'MD10002';
INSERT INTO modelo (cod_modelo,cod_marca,cod_categoria,cod_estilo,nombre_modelo,precio_compra,precio_venta,precio_lista,stock_min) VALUES ('MD10001','MA10001','CT01','ET01','1CEA003',75,150,150,3);
INSERT INTO modelo (cod_modelo,cod_marca,cod_categoria,cod_estilo,nombre_modelo,precio_compra,precio_venta,precio_lista,stock_min) VALUES ('MD10002','MA10002','CT02','ET02','Burkos Cl',120,200,200,5);

-- MODELO_COLOR -> SELECT * FROM modelo_color;
INSERT INTO modelo_color (cod_modelo, cod_color, imagen_url) VALUES ('MD10001','CR02',NULL);
INSERT INTO modelo_color (cod_modelo, cod_color, imagen_url) VALUES ('MD10001','CR03',NULL);
INSERT INTO modelo_color (cod_modelo, cod_color, imagen_url) VALUES ('MD10002','CR04',NULL);
INSERT INTO modelo_color (cod_modelo, cod_color, imagen_url) VALUES ('MD10002','CR05',NULL);

-- CALZADO -> SELECT * FROM calzado;
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100001','MD10001','CR02','TL02',NULL,10);
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100002','MD10001','CR02','TL03',NULL,6);
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100003','MD10001','CR02','TL04',NULL,3);
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100004','MD10001','CR02','TL05',NULL,8);
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100005','MD10001','CR02','TL06',NULL,9);
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100006','MD10001','CR03','TL02',NULL,12);
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100007','MD10001','CR03','TL03',NULL,10);
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100008','MD10001','CR03','TL04',NULL,9);
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100009','MD10001','CR03','TL05',NULL,13);
INSERT INTO calzado (cod_calzado, cod_modelo, cod_color, cod_talla, codigo_br, stock) VALUES ('CZ100010','MD10001','CR03','TL06',NULL,7);

-- CLIENTE -> SELECT * FROM cliente;
INSERT INTO cliente (cod_cliente,cod_distrito,nombre,apellidos,dni,direccion,telefono,email) VALUES ('CL100001','DI02','ANETH LUANA','TINEO URIBE','32425643','AV. LOS GIRASOLES # 1800','990990230','LUANITAHERMOSA@GMAIL.COM');
INSERT INTO cliente (cod_cliente,cod_distrito,nombre,apellidos,dni,direccion,telefono,email) VALUES ('CL100002','DI03','JOSE LUIS','TARAZONA ZELA','78395021','AV. LAS FLORES # 1800',null,'JOSESITO@GMAIL.COM');
INSERT INTO cliente (cod_cliente,cod_distrito,nombre,apellidos,dni,direccion,telefono,email) VALUES ('CL100003','DI05','ANA MARIA','VILLAVICENCIO CASTRO','48502717','AV. LAS FLORES # 2560','989434228','ANITAMARIA@GMAIL.COM');
INSERT INTO cliente (cod_cliente,cod_distrito,nombre,apellidos,dni,direccion,telefono,email) VALUES ('CL100004','DI06','JOSE ANTONIO','ENCISO NOLASCO','86294711','AV. PROCERES DE LA INDEPENDENCIA # 5000','987845874','JOSANTONIO@GMAIL.COM');
INSERT INTO cliente (cod_cliente,cod_distrito,nombre,apellidos,dni,direccion,telefono,email) VALUES ('CL100005','DI05','ALEJANDRA','CHUCO HUERTA','97537923','AV. GRAN CHIMU # 3500','963245874','ALEJANDRITA5245@HOTMAIL.COM');

-- BOLETA -> SELECT * FROM boleta;
INSERT INTO boleta (cod_boleta,cod_cliente,cod_empleado,cod_moneda,fecha_hora_emision,importe_total) VALUES ('BL100001','CL100001','EM10001','MN01',NOW(),750);

-- DETALLE BOLETA -> SELECT * FROM detalle_boleta;
INSERT INTO detalle_boleta (cod_boleta,cod_calzado,cantidad,descuento,importe) VALUES ('BL100001','CZ100001',2,0,300);
INSERT INTO detalle_boleta (cod_boleta,cod_calzado,cantidad,descuento,importe) VALUES ('BL100001','CZ100002',3,0,450);


-- DROP DATABASE db_shoesformen
-- DROP TABLE IF EXISTS distrito;
-- IMPORTANTE : PARA QUE EL VALOR POR DEFAULT DE UN CAMPO FUNCIONE ESTE NO DEBE SER NI MENSIONADO, NO PUEDE SER NI NULL PORQUE NULL YA ES UN VALOR.

/*
SELECT * FROM calzado AS ca 
INNER JOIN modelo_color AS mc ON ca.cod_modelo = mc.cod_modelo AND ca.cod_color = mc.cod_color
INNER JOIN modelo AS mo ON mc.cod_modelo = mo.cod_modelo
INNER JOIN talla AS ta ON ca.cod_talla = ta.cod_talla
*/



-- SELECT CONCAT('EM',CAST(SUBSTRING(MAX(e.cod_empleado),3) AS INT) + 1)  FROM empleado AS e
-- SELECT version();














