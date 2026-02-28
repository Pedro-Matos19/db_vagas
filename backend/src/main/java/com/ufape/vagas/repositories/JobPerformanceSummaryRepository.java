package com.ufape.vagas.repositories;

import com.ufape.vagas.models.JobPerformanceSummary;
import com.ufape.vagas.models.JobPerformanceSummaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPerformanceSummaryRepository extends JpaRepository<JobPerformanceSummary, JobPerformanceSummaryId> {

    @Query(value = "SELECT * FROM v_resumo_desempenho_vagas ORDER BY empresa, vaga LIMIT 5", nativeQuery = true)
    List<JobPerformanceSummary> findTop5Details();
}
