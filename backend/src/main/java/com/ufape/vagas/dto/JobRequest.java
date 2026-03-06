package com.ufape.vagas.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ufape.vagas.enums.JobStatus;

public record JobRequest(
    String title,

    String description,

    String type,

    BigDecimal salary,

    JobStatus status,

    Long companyId,
    
    List<Long> skillsId) {

}
