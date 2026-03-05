package com.ufape.vagas.exceptions;

public class IdNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdNotFoundException() {
		super("Id not found");
	}
	
	
}
