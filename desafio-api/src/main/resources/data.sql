insert ignore into fornecedor (nome, cnpj) values ('Podcat', '9728910657');

insert ignore into produto (id,nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, fornecedor_id) values (1,'Celery Root', '0078-0382', 6.13, false, 78.72, 'Tools', '⁰⁴⁵', 1, 1),
(2,'Trout Rainbow Whole', '63739-670', 22.2, true, 67.67, 'Tools', '¡™£¢∞§¶•ªº–≠', 15, 1),
(3,'Pomello', '55648-945', 41.1, false, 57.45, 'Toys', '1;DROP TABLE users', 68, 1);