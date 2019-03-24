package com.esl.cursospring.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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

	public boolean tokenValido(String token) {
		/*
		 * Claims -> tipo de objeto do JWT que armazena as reivindiações(claims) do token 
		 * quando a pessoa acessa o sistema com o token ela reivindica ser determinado usuario e ter um determinado tempo de expiração
		 */
	     Claims claims = getClaims(token);
	     
	     
	     if(claims != null) {//os tokes foram cpegos com sucesso...
	    	 String username = claims.getSubject();//..obtem o username a partir do claim
	    	 Date expirationDate = claims.getExpiration();//...obtem a data de expiração a partir do claim
	    	 Date now = new Date(System.currentTimeMillis());//...pega a data atual do sistema, para testar se o token está expirado...
	    	 /*
	    	  * testa se tem usuario e data de expiração e o instante atual ainda é anterior a data de expiração 
	    	  */
	    	 if(username != null && expirationDate != null && now.before(expirationDate)) {
	    		 return true;//token válido
	    	 }
	     }
	     return false;
	}

	private Claims getClaims(String token) {
		
		//Se houver problemas com o token irá retornar nulo
		try {
		/*
		 * Recupera os claims a partir de um token
		 */
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}catch(Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
         Claims claims = getClaims(token);
	    
	     if(claims != null) {//os tokes foram pegos com sucesso...
	    	 return claims.getSubject();//..retorna  o username a partir do claim
	   }
	     return null;
	}
}
