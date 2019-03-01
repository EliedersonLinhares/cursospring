package com.esl.cursospring.services;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.esl.cursospring.domain.Cliente;
import com.esl.cursospring.repositories.ClienteRepository;
import com.esl.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired //Automaticamente instaciada pelo spring, injeção de depêndencia
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
		/*
		 * Retorna o objeto se exitir ou jogar uma exceção caso não exista
		 * usa-se uma função lambda para retronar a exceção
		 */
		
		
		/*
		 * Optional<Categoria> obj = repo.findById(id);
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
	
	
}