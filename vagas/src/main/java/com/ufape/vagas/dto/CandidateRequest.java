package com.ufape.vagas.dto;

import java.time.LocalDate;
import java.util.List;

public record CandidateRequest(

    String cpf,

    String name,

    LocalDate birthDate,

    Long userId,
    
    List<Long> skillsId,

    List<Long> coursesId) {}
