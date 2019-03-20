package com.esl.cursospring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.esl.cursospring.domain.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@Column(unique=true)
	private String email;
	private String cpfOuCnpj;
	private Integer tipo;//Armazenado internamente um numero inteiro ao inves de um tipo cliente, sendo qeu para o sistema o dado exposto é um tipocliente
	
	@JsonIgnore //Não exibir a senha durante o get de cliente
	private String senha;
	
	/*Utilizando soment o @jsonIgnore no back reference
	 * @JsonManagedReference //permitir a serialização de produtos, referencia gerenciada pelo jSon,sendo feito no lado que se quer que aparecam os objetos assossiados 
	 */
	/*
	 * Usada quando sua regra de negocio permitir a deleção em cascata, o JPA fara todo o processo
	 * Cascade -> Instrução do JPA que determina o comportamento em cascata 
	 * dessa relação,CascadeType.
	 * ALL -> Toda modificão feita no cliente será refletida no endereço
	 */
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>();//Lista não se coloca nos construtores
	
	
	/*
	 * Implementação de uma classe fraca
	 * 
	 *Sendo feito como um conjunto de strings associado ao cliente, sendo usado "Set" um 
	 *conjunto que não aceita repetição, implementando um hashSet
	 */
	@ElementCollection//
	@CollectionTable(name="TELEFONE")//criação da tabela auxiliar 
	private Set<String> telefones = new HashSet<>();
	
	public Cliente() {
		
	}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha ) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo==null) ? null : tipo.getCod();//Só pega o codigo
		this.senha = senha;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);//Uso do metodo estatico do enum TipoCliente
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();//armzenar somento o codigo
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	
	
}
