package com.ufape.vagas.dto;

import java.math.BigDecimal;
import java.util.List;

public record JobRequest(
    String title,

    String description,

    String type,

    BigDecimal salary,

    String status,

    Long companyId,
    
    List<Long> skillsId) {

}
