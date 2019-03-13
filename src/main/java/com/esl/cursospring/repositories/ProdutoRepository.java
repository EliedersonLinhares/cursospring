package com.esl.cursospring.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esl.cursospring.domain.Categoria;
import com.esl.cursospring.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {//<Tipo, Atributo identificador(do "id" da classe categoria)>
 //JpaRepositry -> tipo especial do spring capaz de acessar os dados com base no tipo passado
	
	/*
	 * Repository para implementar a função de busca paginada usando JPQL para retornar dados baseado
	 * em um pedaço de string  
	 */
	//Usando JPQL
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	//Page<Produto> search(@Param("nome") String nome, @Param("categorias")List<Categoria> categorias, Pageable pageRequest);
	
	//Usando palavaras que montarão a consulta usando padrão de nomes do SpringData do @Query
	@Transactional(readOnly=true)// Apenas uma consulta sem nescessida de um transação
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
