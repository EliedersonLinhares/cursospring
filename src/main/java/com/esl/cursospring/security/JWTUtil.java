package com.esl.cursospring.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String username) {
		//irá gerar o toke para o usuario
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) //Seta a data de expirção do token da hora no sistema + tempo definido no properties
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())//Defini como o token será assinado, setando o algoritimo usado e o segredo que foi definido no properties convertido de string para array de bytes
				.compact();
	}
}
