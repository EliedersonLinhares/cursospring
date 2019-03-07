package com.esl.cursospring.dto;

import java.io.Serializable;

import com.esl.cursospring.domain.Categoria;

/*
 * DATA TRANSFER OBJECT, objeto que terá somente os dados nescessários para a operação
 * , ou projeção de dados
 */
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
		
	}
	
	/*
	 * Criando um construtor para receber o ojeto Categoria da entidade de dominio
	 * , para realizar a função de conversão em CategoriaDTO
	 */
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
