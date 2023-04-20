CREATE DATABASE control_stock

use control_stock


create table producto(
id INT auto_increment,
nombre VARCHAR(50) NOT NULL,
descripcion VARCHAR(255),
cantidad INT NOT NULL DEFAULT 0,
PRIMARY KEY(id)
)ENGINE=InnoDB;

CREATE TABLE CATEGORIA(
id INT auto_increment,
nombre varchar(50) NOT NULL,
PRIMARY KEY(id)
)engine=InnoDB

INSERT INTO `control_stock`.`categoria` (`nombre`) VALUES ('Muebles');
INSERT INTO `control_stock`.`categoria` (`nombre`) VALUES ('Tecnologia');
INSERT INTO `control_stock`.`categoria` (`nombre`) VALUES ('Cocina');
INSERT INTO `control_stock`.`categoria` (`nombre`) VALUES ('Zapatillas');
