CREATE TABLE cliente (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(50) NOT NULL,
	documento VARCHAR(50) NOT NULL,
	data_cadastro DATE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO cliente (nome, email, senha, documento, data_cadastro) values 
('João Silva', 'joao.silva@hotmail.com', "js@123", "123.456.789.10","2020-01-01"),
('Maria Rita', 'maria.rita@hotmail.com', "mr@456", "111.213.141.51","2020-02-02"),
('Pedro Santos', 'pedro.santos@hotmail.com', "ps@789", "617.181.920.21","2020-03-03");