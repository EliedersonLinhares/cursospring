package com.esl.cursospring.resources;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.esl.cursospring.domain.Cliente;
import com.esl.cursospring.repositories.ClienteRepository;
import com.esl.cursospring.services.EmailService;
import com.esl.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();

	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {//se não existtir o email
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();//nova senha
		cliente.setSenha(pe.encode(newPass));//seta  a senha no cliente
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente,newPass);//envia o email para o cliente
	}

	private String newPassword() {//gera uma senha alatoria com letras e numeros
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return  new String(vet);
	}

	private char randomChar() {
		
		int opt = rand.nextInt(3);//quantidade de characteres diferentes
		if(opt == 0) { //gera um digito
			return(char)(rand.nextInt(10) + 48);//1
		}
		else if(opt == 1) {//gera uma letra maiuscula
			return(char)(rand.nextInt(26) + 65);//2
		}
		else {//gera uma letra minuscula
			return(char)(rand.nextInt(26) + 97);//3
		}
		
		/*--1
		 * O numero 10 se refere a quantidade de numeros 0-9
		 * e o numero 48 ao numero da posicão 0 na tabela unicode
		 * 
		 * --2
		 * O numero 26 se refere a quantidade de letras A-Z
		 * e o numero 65 ao numero da posicão A na tabela unicode
		 * 
		 * --3
		 * O numero 26 se refere a quantidade de letras a-z
		 * e o numero 97 ao numero da posicão A na tabela unicode
		 */
	}
	
	
	
	
}
