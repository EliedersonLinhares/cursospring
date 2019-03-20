package com.esl.cursospring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;

	private static final String[] PUBLIC_MATCHERS = {//Definindo um vetor com os caminhos que por padrão estarão liberados
	
			"/h2-console/**"
	               
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {//Definindo um vetor com os caminhos que permitem somente a leitura
		
			"/produtos/**",
			"/categorias/**"
	               
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		 
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
			
			/*
			 * Verifica se o "profile" ativo é o de teste, se for o H2 será usado
			 * será nescessário o comando 
			 * http.headers().frameOptions().disable();
			 * para liberar acesso ao H2
			 */
		}
		
		http.cors().and().csrf().disable();//1//Se tiver um CorsConfiguration definido as suas configurções serão aplicadas
		http.authorizeRequests()
		            .antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()//Permitir somente o metodo GET(leitura) nos caminhos definidos nesse vetor, não podendo altera-los
		            .antMatchers(PUBLIC_MATCHERS).permitAll()//Permissão publica a todos os endpoints do vetor
		            .anyRequest().authenticated();//para todos os outros precisa de autenticação
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//Assegura que o backend não irá criar uma sessão de usuário
	}
	
	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	    
	    /*
	     * Definindo por um bean o CorsConfigurationSource dando um acesso basico
	     * de multiplas fontes para todos os caminhos com as configurações basicas 
	     * 
	     * 1
	     * .and().csrf().disable(); -> Como essa aplicação não armazena dados da sessão(stateless) pode desabilitar
	     * a proteção CSRF(Ataque baseado no armazenamento de autenticação e sessão)
	     */
	  }
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {//metodo que podera ser usado para encritar senhas
		return new BCryptPasswordEncoder();
	}
	
	
	}
