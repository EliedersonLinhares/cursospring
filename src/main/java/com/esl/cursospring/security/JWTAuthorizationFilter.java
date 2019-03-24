package com.esl.cursospring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	private JWTUtil jwtUtil;
	private UserDetailsService userDetailService;//Nescessario para fazer a busca do usuario pelo email
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailService = userDetailService;//
	}
	
	 @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,//Metodo padrão para executar ações antes do metodo de autorização 
	                                    FilterChain chain) throws ServletException, IOException {

		 String header = request.getHeader("Authorization");//Guardando o valor do cabeçalho dentro de uma String
		 
		 /*
		  * Procedimento para libarar o acesso do usuario que está tentando acessar o endpoint
		  */
		 /*
		  * Verifica se o conteudo do header é diferente de nulo e começa com "bearer "...
		  */
		 if(header != null && header.startsWith("Bearer ")) {
			 UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));//..se for pega o token que ve depois do "bearer " com substring
			 if(auth != null) {//como o token retorna nullo se for inválido , faz se essa verificação aqui...
				 SecurityContextHolder.getContext().setAuthentication(auth);// libera a autorização
			 }
		 }
		 chain.doFilter(request, response);
      }

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if(jwtUtil.tokenValido(token)) {// Se o token for valido...
        	String username = jwtUtil.getUsername(token);//extrai o username do token
        	UserDetails user = userDetailService.loadUserByUsername(username);//busca no banco de dados o usuario com esse username 
        	return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
		return null;
	}
}