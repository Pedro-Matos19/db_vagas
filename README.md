# Projeto de Banco de Dados – Sistema de Vagas e Currículos

Este repositório contém o banco de dados do Sistema de Vagas e Currículos, desenvolvido como parte da avaliação da disciplina de Banco de Dados.

O projeto contempla o esquema lógico relacional, normalização, dicionário de dados, scripts SQL de criação e carga de dados, além da execução completa via Docker.

---

## 1. Esquema do Banco de Dados

O banco de dados foi projetado a partir de um Modelo Entidade-Relacionamento Estendido (MERE) e transformado em um esquema lógico relacional.

Características do esquema:

- A entidade Usuario atua como supertipo de Empresa e Estudante, compartilhando o mesmo identificador.
- Relacionamentos muitos-para-muitos foram resolvidos por meio de tabelas associativas.
- Atributos multivalorados foram separados em tabelas próprias.
- O esquema encontra-se normalizado, no mínimo, até a Segunda Forma Normal (2FN).

O diagrama relacional apresenta todas as tabelas, chaves primárias e chaves estrangeiras resultantes da modelagem.

---

## 2. Como rodar o projeto (Docker)

### Pré-requisitos

- Docker
- Docker Compose

### Passos para execução

1. Clone o repositório:

```bash
git clone https://github.com/Pedro-Matos19/db_vagas.git
cd db_vagas

Suba o banco de dados utilizando Docker:

docker compose up -d
Verifique se o container está em execução:

docker ps
Após esses passos, o banco MySQL estará em funcionamento, com todas as tabelas criadas e os dados de teste inseridos automaticamente.

3. Criação e povoamento do banco de dados
A criação das tabelas e a carga de dados são realizadas automaticamente através dos scripts SQL presentes na pasta sql/, montada no container no diretório padrão /docker-entrypoint-initdb.d.

Estrutura dos scripts:

sql/01_ddl.sql: Script de criação das tabelas (DDL).

sql/02_dml.sql: Script de inserção de dados de teste (DML).

Os scripts são executados automaticamente na primeira inicialização do container MySQL.

Recriação do banco
Caso seja necessário recriar o banco e executar novamente os scripts:

docker compose down -v
docker compose up -d
4. Conexão com o banco de dados
O banco pode ser acessado por ferramentas gráficas como MySQL Workbench ou DBeaver utilizando as seguintes configurações:

Host: 127.0.0.1

Porta: 3307

Banco: vagas_db

Usuário: app

Senha: app123

A porta 3307 foi utilizada para evitar conflitos com instalações locais do MySQL.

Também é possível rodar via linha de comando/terminal. basta apenas rodar esse comando e entrar no MySql.
docker exec -it mysql_vagas mysql -uuser -ppassword vagas_db

5. Dicionário de Dados
O dicionário de dados do sistema descreve a finalidade de cada tabela, os atributos, tipos de dados, restrições e a semântica associada a cada campo.

O dicionário pode ser consultado através de:

Documento em PDF incluído no repositório.
📄 [Dicionário de Dados (PDF)](docs/Dicionario_de_dados.pdf)

Metadados do próprio banco de dados, acessíveis via DBeaver (aba Columns e coluna Comment).

Scripts SQL de criação das tabelas.

As informações contemplam:

Descrição da tabela.

Tipos de dados (INT, VARCHAR, DATE, etc.).

Restrições (PK, FK, UNIQUE, NOT NULL, DEFAULT).

Significado de cada atributo.

6. Consultas para validação
Algumas consultas SQL para validação do banco:

SHOW TABLES;

SELECT * FROM Usuario;
SELECT * FROM Empresa;
SELECT * FROM Estudante;
SELECT * FROM Vaga;
SELECT * FROM Candidatura;
Considerações finais
A utilização do Docker garante que qualquer avaliador consiga executar o banco de dados de forma padronizada, sem a necessidade de instalação manual do SGBD ou configurações adicionais no ambiente local.


---
```
