package com.esl.cursospring.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.esl.cursospring.services.exceptions.AuthorizationException;
import com.esl.cursospring.services.exceptions.DataIntegrityException;
import com.esl.cursospring.services.exceptions.ObjectNotFoundException;

@ControllerAdvice //Anotação que permite que se escreva um codigo global para ser aplicado a diferentes controlers ref: https://dzone.com/articles/global-exception-handling-with-controlleradvice
public class ResourceExceptionHandler { //classe auxiliar que intercepta as excessões 
	
	@ExceptionHandler(ObjectNotFoundException.class)//anotado como tratador de excessões
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest  request){//Metodo que recebe a exceção,com as informações da requisição

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis()); //passar os dados do erro; HttpStatus.NOT_FOUND(erro 204)
		
	     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	     
	}     
	
	@ExceptionHandler(DataIntegrityException.class)//anotado como tratador de excessões
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest  request){//Metodo que recebe a exceção,com as informações da requisição

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis()); //passar os dados do erro; HttpStatus.NOT_FOUND(erro 204)
		
	     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	     
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)//anotado como tratador de excessões
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest  request){//Metodo que recebe a exceção,com as informações da requisição

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis()); //passar os dados do erro; HttpStatus.NOT_FOUND(erro 204)
		
		/*
		 * For para percorrer a lista de erros que tem nessa excessão "e", e para cada erro que estiver 
		 * na lista de erros dessa excessão é gerado o objeto FieldMessage
		 * 
		 * e.getBindingResult().getFieldErrors() -> Acessa todos os campos de erros que aconteceram na escessão  MethodArgumentNotValidException
		 */
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
	     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	     
	}
	
	//handler para acesso não autorizado
	@ExceptionHandler(AuthorizationException.class)//anotado como tratador de excessões
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest  request){//Metodo que recebe a exceção,com as informações da requisição

		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis()); //passar os dados do erro; HttpStatus.NOT_FOUND(erro 204)
		
	     return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	     
	}    
	
	
	
	
	/*
	 * Handler para interceptar e tratar o erro de deleção de dados co risco de integridade
	 */
}
