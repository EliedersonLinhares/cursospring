package com.esl.cursospring.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.esl.cursospring.domain.Pedido;

public abstract class AbstractemailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	
	/*
	 * Template Method -> voce consegue implementar um metodo baseados em metodos abstratos
	 * que depois serão implementados pela interface
	 */
	@Override
	public void sendOrderConfirmation(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	/*
	 * Definindo as propriedados do email a ser enviado
	 */
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {

		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());// Destinatario do email, no caso o email do cliente
		sm.setFrom(sender); //remetente, o email padrão definido no application.properties
		sm.setSubject("Pedido confirmado! Código: " + obj.getId()); //assunto do email
		sm.setSentDate(new Date(System.currentTimeMillis()));// data do envio do email 
		sm.setText(obj.toString());//corpo do email usando o toString criado anterirormente
		return sm;
	}

}
