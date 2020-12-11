insert ignore into fornecedor (id,nome, cnpj) 
values (1,'Kabum', '05.570.714/0001-59'),
(2,'Mercado livre', '03.499.243/0001-04'),
(3,'Terabyte', '07.993.973/0001-18');

insert ignore into produto (id, nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, fornecedor_id)
values (1,'HyperX Fury 8GB DRR4', 'HX426C16FB3/80078-0382', 352.82, true, 299.90, 'Hardware', "1566487475_gg.jpg", 8, 1),
(2,'Seagate Expansion 1TB', ' STEA1000400', 339.00, false, null, 'Hardware', '63356_index_gg.jpg', 4, 2),
(3,'AMD Ryzen 5 3600XT', '100-100000281BOX', 1941.38, true, 1689.00, 'Hardware', '100-100000281_98755.png', 16, 3);

insert ignore into venda (id, data_compra, total_compra, cliente_id, fornecedor_id)
values (1, "2020-04-04", 1199.60, 1, 1),
(2, "2020-05-05", 1199.60, 1, 1);

insert ignore into venda_produto (venda_id, produto_id)
values (1,1), (2,1);
