package com.ufape.vagas.repositories;

import com.ufape.vagas.models.DetailsApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsApplicationRepository extends JpaRepository<DetailsApplication, Long> {

    @Query(value = "SELECT * FROM v_detalhes_candidaturas ORDER BY id_candidatura LIMIT 5", nativeQuery = true)
    List<DetailsApplication> findTop5Details();
}
