package com.esl.cursospring.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.esl.cursospring.domain.Pedido;

public abstract class AbstractemailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	protected String htmlFromTemplatePedido(Pedido obj) {//implementando o metodo do envio de email em html
		Context context = new Context();//Context do thymeleaf para passagem de dados
		context.setVariable("pedido", obj);//defini que o thymeleaf irá utilizar o 'obj' com o apelido de 'pedido'
		
		return templateEngine.process("email/confirmacaoPedido", context);//Usa o template engine que foi injetado para setar o caminho do html criado
		
		
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {//prepara o MIME message a partir do pedido
		try {//Envia o email plano caso aconteça um erro com o envio em html
		MimeMessage mm = prepareMimeMessageFromPedido(obj);
		sendHtmlEmail(mm);
		}catch(MessagingException e){
			sendOrderConfirmation(obj);//email texto plano
		}
	}

	private MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}

}
