package com.esl.cursospring.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.esl.cursospring.domain.Pedido;

public interface EmailService {//interface de serviço de email

	void sendOrderConfirmation(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);//envio de email simples
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);//envio de email em html
	
	void sendHtmlEmail(MimeMessage msg);//envio de email em html
}
