package com.esl.cursospring.services;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esl.cursospring.domain.Categoria;
import com.esl.cursospring.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired //Automaticamente instaciada pelo spring, injeção de depêndencia
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
		
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
	
	
}
