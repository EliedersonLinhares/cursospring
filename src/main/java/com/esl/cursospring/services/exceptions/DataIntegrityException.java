package com.esl.cursospring.services.exceptions;

public class DataIntegrityException extends RuntimeException {//exceção personlizada

	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
