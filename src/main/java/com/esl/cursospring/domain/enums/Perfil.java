package com.esl.cursospring.domain.enums;

public enum Perfil {
	
	ADMIN(1,"ROLE_ADMIN"),
	CLIENTE(2,"ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
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
	public static  Perfil toEnum(Integer cod) {//Static -> para ser possivel ser executada mesmo sem instanciar objetos 
		
		if(cod == null) {//Testa se o codigo for nulo
			return null;
		}
		
		/*
		 * Busca entre todo objeto x nos valores possiveis do EstadoPagamento(Pendente,Quitado,Cancelado)
		 * testando se o codigo for igual ao procurado retorna x
		 * Caso o codigo informado for 1 , por exemplo, ele ira retornar o enum PENDENTE
		 */
		for(Perfil x : Perfil.values()) {
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
