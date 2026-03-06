# Projeto de Banco de Dados – Sistema de Vagas e Currículos

Este repositório contém a aplicação completa (Backend, Frontend e Banco de Dados) do **Sistema de Vagas e Currículos**, desenvolvido como parte da avaliação da 2ª VA da disciplina de Banco de Dados.

O projeto contempla o CRUD completo, extração de informações estratégicas através de Visões (Views), esquema lógico relacional, normalização, dicionário de dados, e execução integral via Docker.

## Integrantes do Grupo
- [Pedro Henrique Matos Oliveira](https://github.com/Pedro-Matos19)
- [Antônio Carlos Batista Vaz](https://github.com/AntonioCVaz)
- [João Henrique Araújo de Souza](https://github.com/jota-aga)
- [José Uilton Ferreira de Siqueira](https://github.com/joseuilton)

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

A criação das tabelas, a carga de dados e a geração das visões (Views) são realizadas automaticamente através dos scripts SQL presentes na pasta `sql/`, montada no container no diretório padrão `/docker-entrypoint-initdb.d`.

### 3.1 Estrutura dos scripts

- **`sql/ddl.sql`**: Script de criação das tabelas e relacionamentos (DDL).
- **`sql/dml.sql`**: Script de inserção de dados de teste (DML). O povoamento do banco teve seu conteúdo gerado de forma automatizada usando *prompts* no **Gemini (IA)**. O script atende rigorosamente a todos os requisitos do documento de entrega, garantindo no mínimo 50 registros realistas por tabela principal, mantendo a integridade perfeita das chaves estrangeiras.
- **`sql/views.sql`**: Script contendo a criação das 3 visões (Views) analíticas do sistema, unindo múltiplas tabelas para abstrair consultas complexas.
- **`sql/triggers.sql`**: Script de criação do trigger que atualiza automaticamente o status da candidatura quando uma entrevista é agendada.

Os scripts são executados automaticamente na primeira inicialização do container MySQL.

### 3.2 Recriação do banco
Caso seja necessário recriar o banco e executar novamente os scripts (útil para limpar o banco e forçar a leitura das views e novos dados):

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

### 4.1 Portas da Aplicação
Certifique-se de que as portas abaixo estejam livres na sua máquina para acessar o sistema:
- **Frontend (Interface do Usuário):** `http://localhost:3000`
- **Backend (API):** `http://localhost:8080`
- **Banco de Dados (MySQL):** Porta `3307`

Também é possível acessar o banco via terminal utilizando o comando abaixo:

```bash
docker exec -it mysql_vagas mysql -uuser -ppassword vagas_db
```

---

## 5. Documentação (Dicionário de Dados, Metadados e Normalização)

Toda a documentação estrutural do banco de dados, incluindo a descrição das tabelas, prints da estrutura física e a justificativa das regras de negócio aplicadas na modelagem, está centralizada nesta etapa.

A documentação pode ser consultada através de:

- **Documentos em PDF incluídos no repositório (pasta `doc/`):**
  📄 [Dicionário de Dados (PDF)](doc/Dicionario_de_dados.pdf)
  📄 [Metadados (PDF)](doc/Metadados.pdf)
  📄 [Normalização dos Dados (PDF)](doc/Normalização_Dos_Dados.pdf)

- **Acesso direto via SGBD:** Os metadados também continuam acessíveis diretamente via ferramentas como DBeaver (aba *Columns* e coluna *Comment*).

- **Scripts SQL:** Arquivos de criação das tabelas (`ddl.sql`).

**O Dicionário de Dados contempla:**
- Descrição da finalidade de cada tabela.
- Tipos de dados (INT, VARCHAR, DATE, etc.).
- Restrições (PK, FK, UNIQUE, NOT NULL, DEFAULT).
- Significado de cada atributo.

**O Documento de Metadados contempla:**
- Capturas de tela (prints) detalhadas da estrutura física de todas as 13 tabelas, extraídas diretamente da ferramenta de administração (DBeaver), comprovando a implementação e os tipos de dados adotados.

**O Documento de Normalização contempla:**
- A justificativa rigorosa do processo de modelagem.
- O detalhamento da decomposição de atributos multivalorados para atender à Primeira Forma Normal (1FN).
- O tratamento de dependências em tabelas com chaves compostas para garantir a Segunda Forma Normal (2FN).

---
## 6. Visões SQL (Views) Analíticas

Para abstrair a complexidade do banco de dados, otimizar consultas e facilitar a extração de informações estratégicas (reduzindo a necessidade de múltiplos `JOINs` no backend), foram implementadas **3 Visões (Views)** principais.

Os scripts de criação encontram-se no arquivo `sql/views.sql` e as visões são geradas automaticamente na inicialização do container.

### 6.1 Detalhamento das Visões

1. **`v_detalhes_candidaturas`**
   - **Objetivo**: Fornecer um panorama completo e legível das candidaturas no sistema.
   - **Complexidade abstraída**: Une **4 tabelas** (`Candidatura`, `Estudante`, `Vaga` e `Empresa`).
   - **Utilidade**: Evita a reescrita de consultas complexas no backend ao listar as aplicações do sistema, retornando o nome do candidato, o título da vaga e a empresa de forma direta.

2. **`v_agenda_entrevistas`**
   - **Objetivo**: Consolidar a agenda de entrevistas para facilitar a visualização por painéis de RH ou painéis do aluno.
   - **Complexidade abstraída**: Une **5 tabelas** (`Entrevista`, `Candidatura`, `Estudante`, `Vaga` e `Empresa`).
   - **Utilidade**: Retorna datas, status e os links das reuniões já vinculados aos nomes reais dos candidatos e das empresas envolvidas.

3. **`v_resumo_desempenho_vagas`**
   - **Objetivo**: Gerar um relatório analítico do engajamento e concorrência das vagas.
   - **Complexidade abstraída**: Realiza cálculos de agregação (`COUNT`) unindo as tabelas `Vaga`, `Empresa` e `Candidatura`.
   - **Utilidade**: Processa a contagem de candidatos diretamente no banco de dados, entregando uma métrica pronta (total de candidatos por vaga) sem sobrecarregar a memória da aplicação Java.

---

## 7. Trigger – Atualização automática do status da candidatura

Para manter o **status da candidatura** sempre alinhado ao fluxo do processo seletivo (evitando inconsistências e lógica espalhada no backend), foi implementado **1 trigger** no banco de dados.

O script de criação encontra-se no arquivo `sql/triggers.sql`. O trigger é criado na inicialização do container ou pode ser aplicado manualmente via cliente `mysql`.

### 7.1 Detalhamento do trigger

**`trg_atualiza_status_candidatura_entrevista`**

- **Objetivo**: Garantir que, sempre que uma entrevista for agendada para uma candidatura, o status dessa candidatura passe a refletir essa etapa, sem depender de atualização manual no backend.
- **Regra de negócio automatizada**: *"Ao ser criado um novo registro em `Entrevista` para uma candidatura, o status da candidatura correspondente deve ser atualizado para `ENTREVISTA`."* Assim, a tabela `Candidatura` permanece consistente com a tabela `Entrevista`: se existe entrevista cadastrada, o `status_atual` da candidatura indica que ela está em fase de entrevista.
- **Complexidade abstraída**: O trigger dispara **AFTER INSERT** na tabela `Entrevista` e executa um **UPDATE** na tabela `Candidatura` usando `NEW.id_candidatura`, mantendo a relação entre as duas tabelas e evitando que a aplicação precise lembrar de atualizar o status em toda inserção de entrevista.
- **Utilidade**: Centraliza a regra no banco, reduz erros de esquecimento de atualização no backend e mantém relatórios e visões (como `v_detalhes_candidaturas` e `v_agenda_entrevistas`) sempre com o status correto.

### 7.2 Como testar o trigger

1. **Preparar dados**: Garanta que exista pelo menos uma candidatura (por exemplo, com `status_atual` diferente de `'ENTREVISTA'`, e.g. `'Candidato'` ou `'Em análise'`).

2. **Inserir uma entrevista**: Crie um novo registro em `Entrevista` referenciando o `id_candidatura` dessa candidatura:

   ```sql
   USE vagas_db;

   -- Exemplo: assumindo id_candidatura = 1
   INSERT INTO Entrevista (id_candidatura, data_hora, link_local, status)
   VALUES (1, NOW(), 'https://meet.example.com/abc', 'Agendada');
   ```

3. **Verificar o efeito**: Confira se o status da candidatura foi atualizado automaticamente:

   ```sql
   SELECT id_candidatura, status_atual
   FROM Candidatura
   WHERE id_candidatura = 1;
   ```

   O campo `status_atual` deve exibir `'ENTREVISTA'`.

---

## 8. Ajustes implementados a partir da correção da iteração passada

### 8.1 "Algumas funcionalidades como excluir candidado ou atualizar empresa não estão funcionado, uma mensagem de erro genérica é exibida é "Erro de integridade de dados. Verifique se os dados já estão cadastrados.""

Revisão dos fluxos e iteratividade das telas e ajustes realizados para permitir o CRUD completo de todas as entidades

### 8.2 "Faltou colocar as portas nas quais a aplicação está rodando"
Portas do backend, frontend e banco de dados adicionados ao `README.md`