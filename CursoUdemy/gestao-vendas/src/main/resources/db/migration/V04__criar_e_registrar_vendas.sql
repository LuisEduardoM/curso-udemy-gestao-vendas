CREATE TABLE venda (
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	data DATE NOT NULL,
	codigo_cliente BIGINT NOT NULL,
	FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

INSERT INTO venda(data, codigo_cliente) VALUES ('2021-01-02', 1);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-01-08', 1);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-01-25', 1);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-01-12', 2);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-01-12', 3);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-01-30', 3);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-02-02', 4);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-02-02', 5);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-02-02', 7);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-02-09', 7);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-02-12', 8);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-02-15', 9);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-02-17', 9);
INSERT INTO venda(data, codigo_cliente) VALUES ('2021-02-17', 10);