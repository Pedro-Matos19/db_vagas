package com.ufape.vagas.enums;

public enum UserStatus {
	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String status;

	private UserStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
