package com.esl.cursospring.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"),
	CANCELADO(3,"Cancelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {
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
	 * Recebe um codigo e retorna um objeto do tipo Pagamento já instaciado conforme
	 * o codigo passado
	 * 
	 * Convertendo para enum passando o codigo como parametro
	 */
	public static  EstadoPagamento toEnum(Integer cod) {//Static -> para ser possivel ser executada mesmo sem instanciar objetos 
		
		if(cod == null) {//Testa se o codigo for nulo
			return null;
		}
		
		/*
		 * Busca entre todo objeto x nos valores possiveis do EstadoPagamento(Pendente,Quitado,Cancelado)
		 * testando se o codigo for igual ao procurado retorna x
		 * Caso o codigo informado for 1 , por exemplo, ele ira retornar o enum PENDENTE
		 */
		for(EstadoPagamento x : EstadoPagamento.values()) {
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
