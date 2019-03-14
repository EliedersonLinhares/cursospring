package com.esl.cursospring.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	
	/*Utilizando soment o @jsonIgnore no back reference
	 * @JsonManagedReference //permitir a serialização de produtos, referencia gerenciada pelo jSon,sendo feito no lado que se quer que aparecam os objetos assossiados 
	 */
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")//cascade=CascadeType.ALL -> necessario para evitar erros(Entidade Transiente) quando for salvar o pagamento e o pedido dele
	private Pagamento pagamento;
	
	/*Utilizando soment o @jsonIgnore no back reference
	 * @JsonManagedReference //permitir a serialização de produtos, referencia gerenciada pelo jSon,sendo feito no lado que se quer que aparecam os objetos assossiados 
	 */
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;
	
	
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();//garante que não havera item repetido
	
	public Pedido() {
		
	}
     /*
      * Apesar de mostrado no Modelo, por questoês praticas o pedido deve ser independente do pagamento, para que
      * possa ser instanciado um após o outro 
      */
	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
   
	/*
	 * Exemplo de criação de operações dentro da classe de dominio, de modo
	 * que cada classe seja responsavel por ações reladcionadas a ela
	 */
	//Metodo paara retorna a soma dos itens do pedido
	public double getValorTotal() {
		double soma = 0.0;
		for(ItemPedido ip :itens) {
			soma = soma + ip.getSubTotal();
		}
		return soma;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
