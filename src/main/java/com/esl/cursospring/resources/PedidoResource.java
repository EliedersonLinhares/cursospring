package com.esl.cursospring.resources;



import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.esl.cursospring.domain.Categoria;
import com.esl.cursospring.domain.Pedido;
import com.esl.cursospring.dto.CategoriaDTO;
import com.esl.cursospring.services.PedidoService;

@RestController //Anotação que determina que a classe vai ser um controlador rest
@RequestMapping(value="/pedidos") // respondendo pelo endpoint "/categorias
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // http para obter dados
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
		Pedido obj = service.find(id);//acessando p repository
		
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
	
	@RequestMapping(method=RequestMethod.POST)//Anotação para inserção
	public ResponseEntity<Void> insert(@Valid@RequestBody Pedido obj){//@RequestBody -> converte Json para o objeto Categoria automaticamente
		
		obj = service.insert(obj);
		
		//Pega a URI do novo recurso que foi inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
