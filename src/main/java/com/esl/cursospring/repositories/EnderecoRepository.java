package com.esl.cursospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esl.cursospring.domain.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {//<Tipo, Atributo identificador(do "id" da classe categoria)>
 //JpaRepositry -> tipo especial do spring capaz de acessar os dados com base no tipo passado
}
