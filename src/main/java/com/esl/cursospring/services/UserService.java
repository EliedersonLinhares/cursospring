package com.esl.cursospring.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.esl.cursospring.security.UserSS;

public class UserService {
	
	/*
	 * Retorna o usuario do logado usando a classe definidad anteriormente 
	 */
	public static UserSS authenticated() {
		try {
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//obtem um usuario logado no sistema
		}catch(Exception e) {
			return null;
		}
		}

}
