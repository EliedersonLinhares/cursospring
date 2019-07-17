package com.esl.cursospring.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.esl.cursospring.dto.CredenciaisDTO;


/*
 * Em uma classe que tem o filtro UsernamePasswordAuthenticationFilter
 * o Spring saberá automaticamente que esse filtro irá interceptar a requisição
 * de login ".../login"
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	
	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;
	
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    	setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());// usando a parte de erro de autenticação customizada 
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
	
	
	/*
	 * Metod que tenta autenticar
	 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

		try {
			CredenciaisDTO creds = new ObjectMapper()//pega os dados que vieram na requisição...
	                .readValue(req.getInputStream(), CredenciaisDTO.class);//.. e converte-los para CredenciaisDTO
            /*
             * Com os dados de email e senha será instanciado um objeto do tipo
             * UsernamePasswordAuthenticationToken, incluindo um lista vazia
             */
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
            
	        //verifica se o email e senha são validos
	        Authentication auth = authenticationManager.authenticate(authToken);
	        return auth;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Metodo para definir o que acontece em caso de login bem sucedido
	 * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#successfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain, org.springframework.security.core.Authentication)
	 */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

		String username = ((UserSS) auth.getPrincipal()).getUsername();//pega o email da pessoa que fez o login
        String token = jwtUtil.generateToken(username);//gerando o token
        res.addHeader("Authorization", "Bearer " + token);//adicionando o token no cabeçalho da resposta
        res.addHeader("access-control-expose-headers", "Authorization");
	}
    
    
    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    	/*
    	 * 
    	 * Personalização da mensagem mostrada caso aconteça algun erro na autenticação
    	 */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        response.setStatus(401);
        response.setContentType("application/json"); 
        response.getWriter().append(json());
    }

    //conteudo do JSON
    private String json() {
        long date = new Date().getTime();
        return "{\"timestamp\": " + date + ", "
            + "\"status\": 401, "
            + "\"error\": \"Não autorizado\", "
            + "\"message\": \"Email ou senha inválidos\", "
            + "\"path\": \"/login\"}";
    }
    }
	
}