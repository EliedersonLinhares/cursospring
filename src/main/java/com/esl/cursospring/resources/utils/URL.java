package com.esl.cursospring.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class URL {

	public static List<Integer> decodeIntList(String s){
		//Java 8
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		/*
		 * Arrays.asList(s.split(",")) ---> converter um vetor s.split(",") para lista
		 * .stream().map(x -> Integer.parseInt(x)) ---> para cada elemento "x" da lista é convertido para int 
		 * .collect(Collectors.toList());  ---> Adiciona na lista de inteiros
		 */
		
		
		
		//Java 7
		/*
		 *  String[] vet = s.split(",");  //Split -> função do String que recorta a string a partir do parametro passado
		 *  List<Integer> list = new ArrayList<>();
		 *  for(int i=0; i<vet.length; i++){
		 *      list.add(Integer.parseInt(vet[i]));
		 *  }
		 *  
		 *  return list;
		 */
	}
	/*
	 * Classe auxiliar criada com um metodo que irá pegar os parametros
	 * com virgula na URL (Ex. "1,2,3") e converter em uma lista de 
	 * numeros inteiros
	 * Pegando a string da url, quebrando em partes, converter para inteiro e adicionar na lista
	 */
	
	public static String decodeParam(String s) {
		try {
		return URLDecoder.decode(s, "UTF-8");
		}
		catch(UnsupportedEncodingException e) {
			return "";
		}
		
	/*
	 * Metodo usado para decodificar um parametro passado na URL que contenha espaços	
	 */
		
	}
	
	
	
	
	
	
	
}
