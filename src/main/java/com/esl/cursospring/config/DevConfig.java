package com.esl.cursospring.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.esl.cursospring.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	//Obetendo a linha do arquivo de configuração dev sobre geração do BD
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if (!"create".equals(strategy)) {//testa a variavel strategy for diferente de create não fazer nada 
			return false;
		}//caso contrario instacia a base dados 
		
		dbService.instantiateTestDatabase();
		return true;
	}
	
	
	/*@Profile("dev")
	 * Especifica que todos os beans que estiverem nessa classe serão 
	 * executados somente quando o profile de desenvolvimento 
	 * estiver ativo no 
	 * application.properties
	 * 
	 */
}
