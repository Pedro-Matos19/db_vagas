package com.ufape.vagas.enums;

public enum ApplicationStatus {
	SUBMETIDA("Submetida"),
	EM_ANALISE("Em análise"),
	ENTREVISTA("Entrevista"),
	APROVADO("Aprovado"),
	REJEITADO("Rejeitado"),
	CANCELADA("Cancelada");
	
	private String status;

	private ApplicationStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}	
}
