package com.esl.cursospring.services.exceptions;

public class FileException extends RuntimeException {//exceção personalizada

	private static final long serialVersionUID = 1L;

	public FileException(String msg) {
		super(msg);
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
