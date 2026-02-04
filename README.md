# Projeto de Banco de Dados – Sistema de Vagas e Currículos

Este repositório contém o banco de dados do **Sistema de Vagas e Currículos**, desenvolvido como parte da avaliação da disciplina de Banco de Dados.

O projeto contempla o esquema lógico relacional, normalização, dicionário de dados, scripts SQL de criação e carga de dados, além da execução completa via Docker.

---

## 1. Esquema do Banco de Dados

O banco de dados foi projetado a partir de um **Modelo Entidade-Relacionamento Estendido (MERE)** e transformado em um esquema lógico relacional.

### 1.1 Esquema Conceitual (MERE)
Abaixo está representado o **Modelo Entidade-Relacionamento Estendido (MERE)** atualizado:

![Esquema Conceitual MERE](https://i.ibb.co/1Gjx6LK0/diagrama-1.png)


### 1.2 Esquema Lógico Relacional
O banco de dados foi projetado transformando o MERE em um esquema lógico relacional.

Características da modelagem:
- A entidade **Usuario** atua como supertipo de **Empresa** e **Estudante**, compartilhando o mesmo identificador.
- Relacionamentos muitos-para-muitos foram resolvidos por meio de tabelas associativas.
- Atributos multivalorados foram separados em tabelas próprias.
- O esquema encontra-se normalizado, no mínimo, até a **Segunda Forma Normal (2FN)**.

O diagrama relacional abaixo apresenta todas as tabelas, chaves primárias e chaves estrangeiras resultantes:

![Diagrama Relacional)](https://i.ibb.co/fYYHvP73/sistema-vagas-1.png)

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
```

Inicie o Docker localmente e suba o banco de dados:

```bash
docker compose up -d
```

Verifique se o container está em execução:

```bash
docker ps
```

Após esses passos, o banco MySQL estará em funcionamento, com todas as tabelas criadas e os dados de teste inseridos automaticamente.

---

## 3. Criação e povoamento do banco de dados

A criação das tabelas e a carga de dados são realizadas automaticamente através dos scripts SQL presentes na pasta `sql/`, montada no container no diretório padrão `/docker-entrypoint-initdb.d`.

### 3.1 Estrutura dos scripts

- **`sql/ddl.sql`**: Script de criação das tabelas (DDL).
- **`sql/dml.sql`**: Script de inserção de dados de teste (DML).

Os scripts são executados automaticamente na primeira inicialização do container MySQL.

### 3.2 Recriação do banco
Caso seja necessário recriar o banco e executar novamente os scripts:

```bash
docker compose down -v
docker compose up -d
```

---

## 4. Conexão com o banco de dados

O banco pode ser acessado por ferramentas gráficas como MySQL Workbench ou DBeaver utilizando as seguintes configurações:

- **Host:** 127.0.0.1
- **Porta:** 3307
- **Banco:** vagas_db
- **Usuário:** user
- **Senha:** password

A porta `3307` foi utilizada para evitar conflitos com instalações locais do MySQL.

Também é possível acessar o banco via terminal utilizando o comando abaixo:

```bash
docker exec -it mysql_vagas mysql -uuser -ppassword vagas_db
```

---

## 5. Dicionário de Dados

O dicionário de dados do sistema descreve a finalidade de cada tabela, os atributos, tipos de dados, restrições e a semântica associada a cada campo.

O dicionário pode ser consultado através de:

- **Documento em PDF incluído no repositório:**
  📄 [Dicionário de Dados (PDF)](doc/Dicionario_de_dados.pdf)

- **Metadados do próprio banco de dados:** Acessíveis via DBeaver (aba *Columns* e coluna *Comment*).

- **Scripts SQL:** Arquivos de criação das tabelas.

As informações contemplam:
- Descrição das tabelas
- Tipos de dados (INT, VARCHAR, DATE, etc.)
- Restrições (PK, FK, UNIQUE, NOT NULL, DEFAULT)
- Significado de cada atributo

---

## 6. Consultas para validação
   Algumas consultas SQL para validação do banco:

```bash
- SHOW TABLES;

- SELECT \* FROM Usuario;
- SELECT \* FROM Empresa;
- SELECT \* FROM Estudante;
- SELECT \* FROM Vaga;
- SELECT \* FROM Candidatura;
```

---

## 7. Normalização

O processo de modelagem do banco de dados seguiu rigorosamente as regras de normalização para evitar redundâncias e anomalias. O esquema lógico resultante atende à **Segunda Forma Normal (2FN)**, conforme justificado abaixo:

### Primeira Forma Normal (1FN)
Para atender à 1FN, garantiu-se que todos os atributos fossem atômicos e monovalorados.

- **Decomposição de Multivalorados:** No modelo conceitual, o atributo "telefone" do estudante era multivalorado. Para respeitar a 1FN, este atributo foi removido da tabela `Estudante` e transformado em uma tabela própria denominada `Telefone_Estudante`, onde cada número ocupa uma linha distinta vinculada ao ID do aluno.

### Segunda Forma Normal (2FN)
O esquema está em conformidade com a 2FN, pois não existem dependências parciais nas tabelas que possuem chaves primárias compostas.

- **Tabelas Associativas:** As tabelas oriundas de relacionamentos N:N, como `Estuda` (Estudante-Curso), `Possui` (Estudante-Habilidade) e `Requer` (Vaga-Habilidade), possuem chaves compostas. Nestas tabelas, não existem colunas não-chave que dependam apenas de uma parte da chave.
- **Integridade:** Nas demais tabelas com chaves simples (como `Vaga` ou `Empresa`), todos os atributos dependem totalmente da chave primária inteira.

---

## 8. Carga de Dados e Povoamento

O povoamento do banco de dados foi realizado através da execução de scripts de Manipulação de Dados (DML) contendo comandos `INSERT` padrão da linguagem SQL.

Optou-se pela criação manual de um conjunto de dados fictícios para validar as regras de integridade referencial e simular o funcionamento real do sistema. O processo foi estruturado da seguinte forma:

- **Script de Carga:** Foi desenvolvido um arquivo dedicado (`dml.sql`) contendo as inserções ordenadas para respeitar as dependências das chaves estrangeiras (ex: primeiro cria-se o Usuário, depois o Estudante, depois o Currículo).
- **Automação via Docker:** Para garantir a reprodutibilidade do ambiente, o script de carga foi configurado no container Docker para ser executado automaticamente logo após a criação das tabelas (DDL).
- **Dados de Teste:** Foram inseridos registros em todas as tabelas principais e associativas (como `Candidatura` e `Possui`), permitindo testar consultas e verificar o comportamento das restrições de unicidade e não-nulidade.