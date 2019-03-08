package com.esl.cursospring.resources;



import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.esl.cursospring.domain.Categoria;
import com.esl.cursospring.dto.CategoriaDTO;
import com.esl.cursospring.services.CategoriaService;

@RestController //Anotação que determina que a classe vai ser um controlador rest
@RequestMapping(value="/categorias") // respondendo pelo endpoint "/categorias
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) // http para obter dados
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		Categoria obj = service.find(id);//acessando p repository
		
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
	public ResponseEntity<Void> insert(@Valid@RequestBody CategoriaDTO objDto){//@RequestBody -> converte Json para o objeto Categoria automaticamente
		
		Categoria obj = service.fromDTO(objDto);
		
		obj = service.insert(obj);
		
		//Pega a URI do novo recurso que foi inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//@Valid -> indica uma validação pelo spring
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)//Anotação para atualização
	public ResponseEntity<Void> update(@Valid@RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE) // Anotação para apagar dados
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
	    
		service.delete(id);
		return ResponseEntity.noContent().build();
	
	}
	
	@RequestMapping( method=RequestMethod.GET) // Metodo que implementa o retorno de todas as categorias
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		
		List<Categoria> list = service.findAll();//acessando o repository
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList()); //1
		return ResponseEntity.ok().body(listDto);//retorna o ok com o corpo o objeto obj buscado
	
	/*
	 * 1
	 * 
	 * Percorrendo a lista de categoria usando o Stream(Java 8)
	 * 
	 * list.stream()
	 * 
	 * usando o a Função Map que vai efetuar uma ação para cada elemento da Lista
	 * 
	 * list.stream().map
	 * 
	 * para cada elemento da lista é dado o apelido obj,
	 * é criada um função anonima( -> ) que recebe um obj  instanciando (new) uma
	 * nova categoriaDto passando o obj como argumento
	 * 
	 * list.stream().map(obj -> new CategoriaDTO(obj)
	 * 
	 * e agora é preciso voltar esse tipo obj para o tipo list usando o collect
	 * 
	 * list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList())
	 * 
	 */
	}
	
	@RequestMapping( value="/page",method=RequestMethod.GET) // Metodo que implementa o retorno de todas as categorias de modo Lazy
	public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page, 
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);//acessando o repository
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj)); //1
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
