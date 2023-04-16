CREATE DATABASE control_stock

use control_stock


create table producto(
id INT auto_increment,
nombre VARCHAR(50) NOT NULL,
descripcion VARCHAR(255),
cantidad INT NOT NULL DEFAULT 0,
PRIMARY KEY(id)
)ENGINE=InnoDB;

insert into producto(nombre,descripcion,cantidad) values ('Mesa','mesa de 4 lugares',10)	1 row(s) affected	0.000 sec
insert into producto(nombre,descripcion,cantidad) values ('Celular','Celular Samsung',50)	1 row(s) affected	0.000 sec
