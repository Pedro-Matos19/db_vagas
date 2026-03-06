package com.ufape.vagas.enums;

public enum JobStatus {
	ABERTA("Aberta"),
	FECHADA("Fechada"),
	EM_ANALISE("Em análise"),
	CANCELADA("Cancelada");
	
	private String status;

	JobStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
