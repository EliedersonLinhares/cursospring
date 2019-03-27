package com.esl.cursospring.resources;



import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.esl.cursospring.domain.Pedido;
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
	
	@RequestMapping( method=RequestMethod.GET) // Metodo que implementa o retorno de todas as categorias de modo Lazy
	public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page, 
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="instante") String orderBy, 
            @RequestParam(value="direction", defaultValue="DESC") String direction) {
		
		Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction);//acessando o repository
		
		return ResponseEntity.ok().body(list);//retorna o ok com o corpo o objeto obj buscado
	
		
	/*
	 * Atribuindo o "value=Page" para diferenciar do modo de busca comun
	 * 
	 * Os parametros do findPage não serão parametros como sendo @PathVariable 
	 * e sim um parametro opcional  com  @RequestParam
	 * ex: /categorias/page?page=0&linesPerPage=20
	 * 
	 */
		
	}
}
