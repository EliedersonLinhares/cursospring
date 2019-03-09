package com.esl.cursospring.resources;



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

import com.esl.cursospring.domain.Cliente;
import com.esl.cursospring.dto.ClienteDTO;
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
	
	//@Valid -> indica uma validação pelo spring
		@RequestMapping(value="/{id}", method=RequestMethod.PUT)//Anotação para atualização
		public ResponseEntity<Void> update(@Valid@RequestBody ClienteDTO objDto, @PathVariable Integer id){
			Cliente obj = service.fromDTO(objDto);
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
		public ResponseEntity<List<ClienteDTO>> findAll() {
			
			List<Cliente> list = service.findAll();//acessando o repository
			List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList()); //1
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
		 * list.stream().map(obj -> new ClienteDTO(obj)
		 * 
		 * e agora é preciso voltar esse tipo obj para o tipo list usando o collect
		 * 
		 * list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList())
		 * 
		 */
		}
		
		@RequestMapping( value="/page",method=RequestMethod.GET) // Metodo que implementa o retorno de todas as categorias de modo Lazy
		public ResponseEntity<Page<ClienteDTO>> findPage(
	            @RequestParam(value="page", defaultValue="0") Integer page, 
	            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
	            @RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
	            @RequestParam(value="direction", defaultValue="ASC") String direction) {
			
			Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);//acessando o repository
			Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj)); //1
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
