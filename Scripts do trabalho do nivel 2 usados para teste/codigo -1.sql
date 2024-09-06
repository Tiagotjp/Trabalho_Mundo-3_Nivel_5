CREATE DATABASE Loja;
GO

USE Loja;
GO

CREATE SEQUENCE ordemPessoaId
START WITH 1
INCREMENT BY 1;

CREATE TABLE Pessoa (
  idPessoa INTEGER NOT NULL CONSTRAINT PK_Pessoa PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  logradouro VARCHAR(255),
  cidade VARCHAR(255),
  estado CHAR(2) NOT NULL,
  telefone VARCHAR(11),
  email VARCHAR(255)
);
GO

CREATE TABLE PessoaFisica (
  idPessoa INTEGER NOT NULL CONSTRAINT PK_PessoaFisica PRIMARY KEY,
  cpf VARCHAR(11) NOT NULL,
  CONSTRAINT FK_PessoaFisica_Pessoa FOREIGN KEY (idPessoa) REFERENCES Pessoa (idPessoa)
);
GO

CREATE TABLE PessoaJuridica (
  idPessoa INTEGER NOT NULL CONSTRAINT PK_PessoaJuridica PRIMARY KEY,
  cnpj VARCHAR(14) NOT NULL,
  CONSTRAINT FK_PessoaJuridica_Pessoa FOREIGN KEY (idPessoa) REFERENCES Pessoa (idPessoa)
);
GO

CREATE TABLE Usuario (
  idUsuario INTEGER NOT NULL CONSTRAINT PK_Usuario PRIMARY KEY IDENTITY,
  login VARCHAR(20) NOT NULL,
  senha VARCHAR(20) NOT NULL
);
GO

CREATE TABLE Produto (
  idProduto INTEGER NOT NULL CONSTRAINT PK_Produto PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  quantidade INTEGER,
  precoVenda NUMERIC(5, 2)
);
GO

CREATE TABLE Movimento (
  idMovimento INTEGER NOT NULL CONSTRAINT PK_Movimento PRIMARY KEY,
  idUsuario INTEGER NOT NULL,
  idPessoa INTEGER NOT NULL,
  idProduto INTEGER,
  quantidade INTEGER,
  tipo CHAR(1),
  valorUnitario NUMERIC (5, 2),
  CONSTRAINT FK_Movimento_Usuario FOREIGN KEY (idUsuario) REFERENCES Usuario (idUsuario),
  CONSTRAINT FK_Movimento_Pessoa FOREIGN KEY (idPessoa) REFERENCES Pessoa (idPessoa),
  CONSTRAINT FK_Movimento_Produto FOREIGN KEY (idProduto) REFERENCES Produto (idProduto)
);
GO
