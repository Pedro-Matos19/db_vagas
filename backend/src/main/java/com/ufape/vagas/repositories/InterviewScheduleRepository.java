package com.ufape.vagas.repositories;

import com.ufape.vagas.models.InterviewSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {

    @Query(value = "SELECT * FROM v_agenda_entrevistas ORDER BY id_entrevista LIMIT 5", nativeQuery = true)
    List<InterviewSchedule> findTop5Details();
}
