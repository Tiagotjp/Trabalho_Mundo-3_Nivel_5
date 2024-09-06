USE Loja;

INSERT INTO Usuario (login, senha)
VALUES ('op1', 'op1'),
('op2', 'op2'),
('op3', 'op3'),
('op4', 'op4');

INSERT INTO Produto (idProduto, nome, quantidade, precoVenda)
VALUES ('1', 'Cubo de Roda', '100', '60.00'),
('3', 'Disco de Freio', '200', '290.00'),
('4', 'Pastilha de Freio', '400', '55.00');

INSERT INTO Pessoa (idPessoa, nome, logradouro, cidade, estado, telefone, email)
VALUES 
(NEXT VALUE FOR ordemPessoaId, 'Ana', 'Rua T, 20', 'Vitoria', 'ES', '1111-1111', 'ana@gmail.com'),
(NEXT VALUE FOR ordemPessoaId, 'Bruno', 'Rua N, 30', 'Santos', 'SP', '2222-2222', 'bruno@gmail.com'),
(NEXT VALUE FOR ordemPessoaId, 'Claudio', 'Rua W, 40', 'Cariacica', 'ES', '3333-3333', 'claudio@gmail.com'),
(NEXT VALUE FOR ordemPessoaId, 'Distribuidora Vespor', 'Avenida J, 80', 'Vitoria', 'ES', '4444-4444', 'vespord@gmail.com'),
(NEXT VALUE FOR ordemPessoaId, 'Empresa Vespor', 'Avenida H, 70', 'Vila Velha', 'ES', '5555-5555', 'vesporv@gmail.com');

INSERT INTO PessoaFisica (idPessoa, cpf)
VALUES (1, '11111111111'),
(2, '22222222222'),
(3, '33333333333');

INSERT INTO PessoaJuridica (idPessoa, cnpj)
VALUES (4, '44444444444444'),
(5, '55555555555555');

INSERT INTO Movimento (idMovimento, idUsuario, idPessoa, idProduto, quantidade, tipo, valorUnitario)
VALUES (1, 1, 5, 1, 40,'E', 40.00),
(3, 2, 3, 3, 20,'S', 30.00),
(5, 1, 4, 4, 60,'E', 55.00),
(6, 2, 1, 1, 15,'S', 40.00),
(7, 4, 2, 4, 25,'S', 35.00),
(9, 3, 5, 3, 50,'E', 45.00);
