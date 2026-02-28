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