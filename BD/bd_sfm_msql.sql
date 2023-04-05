DROP DATABASE IF EXISTS bd_sfm_msql;
CREATE DATABASE  bd_sfm_msql;
USE bd_sfm_msql;

CREATE TABLE empresa(
	codigo       CHAR(3) PRIMARY KEY,
	ruc          CHAR(11) UNIQUE,
	nombre       VARCHAR(50) NOT NULL,
	direccion    VARCHAR(50),
	distrito     VARCHAR(50),
	telefono     VARCHAR(11),
	imagen       VARCHAR(200)
);

-- EMPRESA
INSERT INTO empresa (codigo,ruc,nombre,direccion,distrito,telefono,imagen) VALUE ('EP1','44445555777','Shoes For Men','Av. Carlos Izaguirre 233','Independencia','000-0000','https://raw.githubusercontent.com/paledot02/Img_Calzados/main/logo_reporte_01.png');
-- https://raw.githubusercontent.com/paledot02/Img_Calzados/main/logo_reporte_02.png
-- SELECT * FROM empresa;