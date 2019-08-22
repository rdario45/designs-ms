# --- Creation of events table

# --- !Ups
CREATE TABLE designs (
	id serial NOT NULL,
	nombre varchar(100) NOT NULL,
	autor varchar(100) NOT NULL,
	precio numeric(10,2) NOT NULL,
	filename varchar(200) NULL,
	original BYTEA NULL,
	procesado BYTEA NULL,
	estado varchar(20) NOT NULL,
	fecha timestamp NOT NULL
);
COMMENT ON TABLE designs IS 'Almacena los diseños';

# --- !Downs
drop table pictures;