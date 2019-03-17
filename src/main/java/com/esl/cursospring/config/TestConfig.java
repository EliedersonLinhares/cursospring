package com.esl.cursospring.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.esl.cursospring.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	
	/*@Profile("Test")
	 * Especifica que todos os beans que estiverem nessa classe serão 
	 * executados somente quando o profile de teste estiver ativo no 
	 * application.properties
	 */
}
