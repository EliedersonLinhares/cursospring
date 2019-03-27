package com.esl.cursospring.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esl.cursospring.domain.Cliente;
import com.esl.cursospring.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {//<Tipo, Atributo identificador(do "id" da classe categoria)>
 //JpaRepositry -> tipo especial do spring capaz de acessar os dados com base no tipo passado
	
	//Procurar pedido por cliente
	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
