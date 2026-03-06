USE vagas_db;

DELIMITER //

DROP TRIGGER IF EXISTS trg_atualiza_status_candidatura_entrevista;

CREATE TRIGGER trg_atualiza_status_candidatura_entrevista
AFTER INSERT ON Entrevista
FOR EACH ROW
BEGIN
    UPDATE Candidatura
    SET status_atual = 'ENTREVISTA'
    WHERE id_candidatura = NEW.id_candidatura;
END;
//

DELIMITER ;
