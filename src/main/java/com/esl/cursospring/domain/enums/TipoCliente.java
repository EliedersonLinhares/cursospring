package com.esl.cursospring.domain.enums;

public enum TipoCliente {
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	/*
	 * Recebe um codigo e retorna um objeto do tipo Cliente já instaciado conforme
	 * o codigo passado
	 * 
	 * Convertendo para enum passando o codigo como parametro
	 */
	public static  TipoCliente toEnum(Integer cod) {//Static -> para ser possivel ser executada mesmo sem instanciar objetos 
		
		if(cod == null) {//Testa se o codigo for nulo
			return null;
		}
		
		/*
		 * Busca entre todo objeto x nos valores possiveis do tipoCliente(PessoaFisica e PessoaJuridica)
		 * testando se o codigo for igual ao procurado retorna x
		 * Caso o codigo informado for 1 , por exemplo, ele ira retornar o enum PESSOAFISICA
		 */
		for(TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			  }
			}
		
		/*
		 * Caso não seja nenhum dos codigos validos joga uma excessão do java
		 */
		throw new IllegalArgumentException("Id inválido: " + cod);
		
	}

}
