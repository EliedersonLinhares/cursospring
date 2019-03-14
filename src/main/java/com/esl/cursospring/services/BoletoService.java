package com.esl.cursospring.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.esl.cursospring.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	//Metodo para gerar uma data de vencimento sete dias depois da data atual
	/*
	 * Em uma situação real deve trocar a implementação pelo que o WebService de pagamento
	 * de pagamento que esteja usando com os dados que lhe passarem
	 */
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}

}
