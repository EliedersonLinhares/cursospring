package com.esl.cursospring.services.exceptions;

public class AuthorizationException extends RuntimeException {//exceção personalizada

	private static final long serialVersionUID = 1L;

	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
