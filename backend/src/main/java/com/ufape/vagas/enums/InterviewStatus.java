package com.ufape.vagas.enums;

public enum InterviewStatus {
	AGENDADA("Agendada"),
	REALIZADA("Realizada"),
	REMARCADA("Remarcada"),
	CANCELADA("Cancelada");
	
	private String status;

	private InterviewStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
