package com.esl.cursospring.resources;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esl.cursospring.domain.Produto;
import com.esl.cursospring.dto.ProdutoDTO;
import com.esl.cursospring.resources.utils.URL;
import com.esl.cursospring.services.ProdutoService;

@RestController //Anotação que determina que a classe vai ser um controlador rest
@RequestMapping(value="/produtos") // respondendo pelo endpoint "/categorias
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // http para obter dados
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
		Produto obj = service.find(id);//acessando p repository
		
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
	
	@RequestMapping( method=RequestMethod.GET) // Metodo que implementa o retorno de todas as categorias de modo Lazy
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			
			 @RequestParam(value="nome", defaultValue="") String nome,
			 @RequestParam(value="categorias", defaultValue="") String categorias, 
			
            @RequestParam(value="page", defaultValue="0") Integer page, 
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids,page, linesPerPage, orderBy, direction);//acessando o repository
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj)); //1
		return ResponseEntity.ok().body(listDto);//retorna o ok com o corpo o objeto obj buscado
	
		
	/*
	 * Atribuindo o "value=Page" para diferenciar do modo de busca comun
	 * 
	 * Os parametros do findPage não serão parametros como sendo @PathVariable 
	 * e sim um parametro opcional  com  @RequestParam
	 * ex: /categorias/page?page=0&linesPerPage=20
	 * 
	 * 1
	 * 
	 * O Page do SpringData já é um metodo feito com java 8, então a função poder ser resumida sem o stream e o collect
	 * 	
	 */
		
	}
	
	
}
