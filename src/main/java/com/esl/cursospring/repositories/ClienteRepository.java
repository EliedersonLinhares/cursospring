package com.esl.cursospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esl.cursospring.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {//<Tipo, Atributo identificador(do "id" da classe categoria)>
 //JpaRepositry -> tipo especial do spring capaz de acessar os dados com base no tipo passado
	
	
	/*
	 * busca por email
	 * Escrevendo o nome do metodo com as letras findByEmail o Spring automaticamente
	 * vai detectar que você quer fazer uma busca por email e irá implementar o 
	 * método.
	 * @Transactional(readOnly=true) -> não necessita ser envolvida como uma transação
	 * de banco de dados, fazendo essa operação ficar mais rápida e reduzindo lock-in de 
	 * recursos
	 */
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}
