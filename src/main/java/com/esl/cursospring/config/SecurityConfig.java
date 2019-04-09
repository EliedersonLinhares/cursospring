package com.esl.cursospring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.esl.cursospring.security.JWTAuthenticationFilter;
import com.esl.cursospring.security.JWTAuthorizationFilter;
import com.esl.cursospring.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//Anotação usada para permitir o uso de perfis nos endpoints
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	/*
	 * è injetado a interface UserDetailsService, o Spring consegue buscar a
	 * implementação dessa classe a UserDetailsServiceImpl injetando uma instancia dela
	 */
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;

	private static final String[] PUBLIC_MATCHERS = {//Definindo um vetor com os caminhos que por padrão estarão liberados
	
			"/h2-console/**"
	               
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {//Definindo um vetor com os caminhos que permitem somente a leitura
		
			"/produtos/**",
			"/categorias/**"
	               
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = {//Definindo um vetor com os caminhos que permitem inserção
			
			"/clientes",
			"/clientes/picture",
			"/auth/forgot/**"
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
		            .antMatchers(HttpMethod.POST,PUBLIC_MATCHERS_POST).permitAll()
		            .antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()//Permitir somente o metodo GET(leitura) nos caminhos definidos nesse vetor, não podendo altera-los
		            .antMatchers(PUBLIC_MATCHERS).permitAll()//Permissão publica a todos os endpoints do vetor
		            .anyRequest().authenticated();//para todos os outros precisa de autenticação
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));//Disponivel depois de feito o filtro de login
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));//Disponivel depois de feito o filtro de autorização
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//Assegura que o backend não irá criar uma sessão de usuário
	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		//defini que em é o userDetailsService e o passwordEncoder
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
