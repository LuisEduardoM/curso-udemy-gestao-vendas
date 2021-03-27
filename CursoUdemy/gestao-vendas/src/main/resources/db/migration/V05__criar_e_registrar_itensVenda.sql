CREATE TABLE item_venda (
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	codigo_produto BIGINT NOT NULL,
	codigo_venda BIGINT NOT NULL,
	quantidade INTEGER NOT NULL,
	preco_vendido DECIMAL(10,2) NOT NULL,
	FOREIGN KEY (codigo_produto) REFERENCES produto(codigo),
	FOREIGN KEY (codigo_venda) REFERENCES venda(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (1, 1, 1, 870);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (15, 1, 1, 249);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (2, 2, 1, 1623.20);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (3, 3, 1, 1073.36);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (4, 4, 1, 1899);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (5, 5, 1, 3300);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (6, 6, 1, 700);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (7, 7, 1, 800);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (8, 8, 1, 900);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (9, 9, 3, 419.70);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (16, 9, 1, 160.50);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (10, 10, 1, 106.80);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (17, 10, 1, 299.90);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (11, 11, 1, 424.86);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (12, 12, 1, 1164.94);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (13, 13, 1, 415.90);
INSERT INTO item_venda(codigo_produto, codigo_venda, quantidade, preco_vendido) VALUES (14, 14, 1, 1370);