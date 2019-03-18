package com.esl.cursospring.services;

import org.springframework.mail.SimpleMailMessage;

import com.esl.cursospring.domain.Pedido;

public interface EmailService {//interface de serviço de email

	void sendOrderConfirmation(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
