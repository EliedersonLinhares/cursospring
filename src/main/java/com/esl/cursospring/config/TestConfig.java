package com.esl.cursospring.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.esl.cursospring.services.DBService;
import com.esl.cursospring.services.EmailService;
import com.esl.cursospring.services.MockEmailService;

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
	
	/*
	 * Quando um metodo é anotado como @bean ele estara disponivel
	 * para o sitema, se houver uma injeção de dependencia(@Autowired)
	 * o spring ira procurar um @bean dessa mesma asssinatura que ira retornar
	 * uma nova instancia desse objeto
	 */
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
	/*@Profile("Test")
	 * Especifica que todos os beans que estiverem nessa classe serão 
	 * executados somente quando o profile de teste estiver ativo no 
	 * application.properties
	 */
}
