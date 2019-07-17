package com.esl.cursospring.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esl.cursospring.dto.EmailDTO;
import com.esl.cursospring.security.JWTUtil;
import com.esl.cursospring.security.UserSS;
import com.esl.cursospring.services.UserService;

/*Classe usada para o refresh token(adicionado tempo ao 
 * token atual, quando este estiver perto de expirar
*/
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	//endpoint para renovação do token
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)//protegido por autenticação
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();//pega o usuario logado
		String token = jwtUtil.generateToken(user.getUsername());//gera um novo token, atualizado
		response.addHeader("Authorization", "Bearer " + token);//adiciona o token atualizado no corpo da requisição
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	//endpoint para envio do email para nova senha
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}

}
