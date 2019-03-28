package com.esl.cursospring.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esl.cursospring.security.JWTUtil;
import com.esl.cursospring.security.UserSS;
import com.esl.cursospring.services.UserService;

/*Classe usada para o refresh token(adicionado tempo ao 
 * token atual, quando este estiver perto de expirar
*/
@RestController
@RequestMapping(value="/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)//protegido por autenticação
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();//pega o usuario logado
		String token = jwtUtil.generateToken(user.getUsername());//gera um novo token, atualizado
		response.addHeader("Authorization", "Bearer " + token);//adiciona o token atualizado no corpo da requisição
		return ResponseEntity.noContent().build();
	}

}
