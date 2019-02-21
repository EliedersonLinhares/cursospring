package com.esl.cursospring.resources;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esl.cursospring.domain.Categoria;
import com.esl.cursospring.services.CategoriaService;

@RestController //Anotação que determina que a classe vai ser um controlador rest
@RequestMapping(value="/categorias") // respondendo pelo endpoint "/categorias
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // http para obter dados
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Categoria obj = service.buscar(id);//acessando p repository
		
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
