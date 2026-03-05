CREATE DATABASE IF NOT EXISTS vagas_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vagas_db;

set foreign_key_checks = 0;

CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    e_mail VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
	status VARCHAR(30) DEFAULT 'ATIVO'
);
CREATE TABLE Empresa (
    id_empresa INT PRIMARY KEY ,
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    razao_social VARCHAR(80) NOT NULL,
    localizacao VARCHAR(50),
    site VARCHAR(1000),
    setor_atuacao VARCHAR (30),
    descricao LONGTEXT,
    CONSTRAINT fk_empresa_id_usuario
        FOREIGN KEY (id_empresa) REFERENCES Usuario(id_usuario)
);
CREATE TABLE Estudante (
    id_estudante INT PRIMARY KEY,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE,
    CONSTRAINT fk_estudante_usuario_id
        FOREIGN KEY (id_estudante) REFERENCES Usuario(id_usuario)
);
CREATE TABLE Telefone (
    id_telefone INT PRIMARY KEY AUTO_INCREMENT,
    id_estudante INT NOT NULL,
    numero VARCHAR(13) NOT NULL,
    CONSTRAINT fk_telefone_id_estudante
        FOREIGN KEY (id_estudante) REFERENCES Estudante(id_estudante)
);
CREATE TABLE Curriculo (
    id_curriculo INT PRIMARY KEY AUTO_INCREMENT,
    id_estudante INT NOT NULL UNIQUE,
    objetivo LONGTEXT,
    experiencia MEDIUMTEXT,
    link_portifolio VARCHAR(1000),
    CONSTRAINT fk_curriculo_id_estudante
        FOREIGN KEY (id_estudante) REFERENCES Estudante(id_estudante)
);
CREATE TABLE Habilidade (
    id_habilidade INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL
);
CREATE TABLE Possui (
    id_estudante INT NOT NULL,
    id_habilidade INT NOT NULL,
    PRIMARY KEY(id_estudante, id_habilidade),
    CONSTRAINT fk_possui_id_estudante
        FOREIGN KEY (id_estudante) REFERENCES Estudante(id_estudante),
    CONSTRAINT fk_possui_id_habilidade
        FOREIGN KEY (id_habilidade) REFERENCES Habilidade(id_habilidade)
);
CREATE TABLE Curso (
    id_curso INT PRIMARY KEY AUTO_INCREMENT,
    nome_curso VARCHAR(80) NOT NULL,
    nivel VARCHAR(50)
);
CREATE TABLE Estuda (
    id_estudante INT NOT NULL,
    id_curso INT NOT NULL,
    PRIMARY KEY(id_estudante, id_curso),
    CONSTRAINT fk_estuda_id_estudante
        FOREIGN KEY (id_estudante) REFERENCES Estudante(id_estudante),
    CONSTRAINT fk_estuda_id_curso
        FOREIGN KEY (id_curso) REFERENCES Curso(id_curso)
);
CREATE TABLE Vaga (
    id_vaga INT PRIMARY KEY AUTO_INCREMENT,
    id_empresa INT NOT NULL,
    titulo VARCHAR(50) NOT NULL,
    descricao LONGTEXT,
    tipo VARCHAR(50),
    bolsa_salario DECIMAL(10,2),
    status VARCHAR(30) DEFAULT 'Aberta',
    CONSTRAINT fk_vaga_id_empresa
        FOREIGN KEY (id_empresa) REFERENCES Empresa(id_empresa)
);
CREATE TABLE Requer (
    id_vaga INT NOT NULL,
    id_habilidade INT NOT NULL,
    PRIMARY KEY(id_vaga, id_habilidade),
    CONSTRAINT fk_requer_id_vaga
        FOREIGN KEY (id_vaga) REFERENCES Vaga(id_vaga),
    CONSTRAINT fk_requer_id_habilidade
        FOREIGN KEY (id_habilidade) REFERENCES Habilidade(id_habilidade)
);
CREATE TABLE Candidatura (
    id_candidatura INT PRIMARY KEY AUTO_INCREMENT,
    id_estudante INT NOT NULL,
    id_vaga INT NOT NULL,
    data_aplicacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    status_atual VARCHAR(30),
    CONSTRAINT fk_candidatura_estudante
		FOREIGN KEY (id_estudante) REFERENCES Estudante(id_estudante),
	CONSTRAINT fk_candidatura_vaga
		FOREIGN KEY (id_vaga) REFERENCES Vaga(id_vaga)
);
CREATE TABLE Entrevista (
    id_entrevista INT PRIMARY KEY AUTO_INCREMENT,
    id_candidatura INT NOT NULL,
    data_hora DATETIME NOT NULL,
    link_local VARCHAR(1000),
    feedback VARCHAR(1000),
    status VARCHAR(30),
    CONSTRAINT fk_entrevista_id_candidatura
        FOREIGN KEY (id_candidatura) REFERENCES Candidatura(id_candidatura)
);

set foreign_key_checks = 1;

CREATE OR REPLACE VIEW v_detalhes_candidaturas AS
SELECT 
    c.id_candidatura,
    e.nome AS nome_candidato,
    v.titulo AS titulo_vaga,
    emp.razao_social AS nome_empresa,
    c.data_aplicacao,
    c.status_atual AS status_candidatura
FROM Candidatura c
INNER JOIN Estudante e ON c.id_estudante = e.id_estudante
INNER JOIN Vaga v ON c.id_vaga = v.id_vaga
INNER JOIN Empresa emp ON v.id_empresa = emp.id_empresa;

CREATE OR REPLACE VIEW v_agenda_entrevistas AS
SELECT 
    ent.id_entrevista,
    ent.data_hora AS data_da_entrevista,
    est.nome AS candidato,
    v.titulo AS vaga,
    emp.razao_social AS empresa,
    ent.link_local AS link_reuniao,
    ent.status AS status_entrevista
FROM Entrevista ent
INNER JOIN Candidatura c ON ent.id_candidatura = c.id_candidatura
INNER JOIN Estudante est ON c.id_estudante = est.id_estudante
INNER JOIN Vaga v ON c.id_vaga = v.id_vaga
INNER JOIN Empresa emp ON v.id_empresa = emp.id_empresa;

CREATE OR REPLACE VIEW v_resumo_desempenho_vagas AS
SELECT 
    emp.razao_social AS empresa,
    v.titulo AS vaga,
    v.status AS status_vaga,
    v.bolsa_salario,
    COUNT(c.id_candidatura) AS total_candidatos
FROM Vaga v
INNER JOIN Empresa emp ON v.id_empresa = emp.id_empresa
LEFT JOIN Candidatura c ON v.id_vaga = c.id_vaga
GROUP BY 
    emp.razao_social, 
    v.titulo, 
    v.status, 
    v.bolsa_salario;
