package com.ufape.vagas.repositories;

import com.ufape.vagas.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByCandidateId(Long candidateId);
}
