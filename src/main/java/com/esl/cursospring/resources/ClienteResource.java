package com.esl.cursospring.resources;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esl.cursospring.domain.Cliente;
import com.esl.cursospring.services.ClienteService;

@RestController //Anotação que determina que a classe vai ser um controlador rest
@RequestMapping(value="/clientes") // respondendo pelo endpoint "/categorias
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // http para obter dados
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = service.find(id);//acessando p repository
		
		return ResponseEntity.ok().body(obj);//retorna o ok com o corpo o objeto obj buscado
	}
	
	/*
	 * value="/{id}" -> Endpoint com uso de algum id que for digitado
	 * 
	 * (@PathVariable Integer id) -> receberá um id que vai vir na url
	 * 
	 * ResponseEntity<?> -> Tipo espcial do spring que encapsula varias informações
	 * de uma resposta Http para um serviço REST
	 * 
	 */
	
}
