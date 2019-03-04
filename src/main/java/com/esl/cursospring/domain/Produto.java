package com.esl.cursospring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private  Integer id;
	private String nome;
	private double preco;
	
	@JsonBackReference //Omitir a lista de categoria para cada produto pois a busca já foi feita no outro lado(Categoria)
	@ManyToMany
	@JoinTable(name= "PRODUTO_CATEGORIA",//tabela do meio entre Produto e categorias
	           joinColumns = @JoinColumn(name = "produto_id"),//chave strangeira na classe produto
	           inverseJoinColumns = @JoinColumn(name = "categoria_id")) //segunda chave estrangeira que ira referenciar a classe categoria)
	private List<Categoria> categorias = new ArrayList<>();
	
	@OneToMany(mappedBy="id.produto")
	private Set<ItemPedido> itens = new HashSet<>();//garante que não havera item repetido
	

	
	public Produto() {
		
	}

	public Produto(Integer id, String nome, double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	
	/*
	 * Criação de um metodo que varrendos os ItensPedidos que montara uma
	 * lista de pedidos associados a esses itens
	 */
	public List<Pedido> getPedidos(){
		List<Pedido> lista = new ArrayList<>();//inicia uma lista de pedidos
		for(ItemPedido x : itens) {//Percorre a lista intens
			lista.add(x.GetPedido());//para cada itemPedido x  que exisitir na lista de itens,será adicionado o pedido associado a ele na lista
		}
		return lista;
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

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	

}
