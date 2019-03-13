package com.esl.cursospring.services;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.esl.cursospring.domain.Categoria;
import com.esl.cursospring.domain.Produto;
import com.esl.cursospring.repositories.CategoriaRepository;
import com.esl.cursospring.repositories.ProdutoRepository;
import com.esl.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired //Automaticamente instaciada pelo spring, injeção de depêndencia
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
		
		/*
		 * Retorna o objeto se exitir ou jogar uma exceção caso não exista
		 * usa-se uma função lambda para retronar a exceção
		 */
		
		
		/*
		 * Optional<Produto> obj = repo.findById(id);
		return obj.orElse(null);
		 */
		/*
		 * O SpringBoot a partir da versão 2.x passou a usar o java 8 e com isso
		 * usa o objeto Optional(Container, encapsula se o objeto está instaciado ou não(null))
		 *  para manter o mesmo contrato,
		 * Usando o findById ele retornará um objeto Optional do tipo que voce precisar
		 * 
		 * obj.orElse(null) ->  Se o objeto for encontrado ele vai ter sido instaciado, 
		 * retorna o objeto. Se o objeto não for encontrado irá retornar o valor nulo
		 */
	}
	
	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction){
	
		//Objeto que prepara os dados para retonar a consulta com a pagina de dados, tambem é um metodo od SpringData
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);//lista instanciada a partir da lista de ids, buscado todas as categorias que tiverem esse id na lista
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}
	
	
	
}
