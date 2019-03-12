package com.esl.cursospring.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.esl.cursospring.domain.Cliente;
import com.esl.cursospring.dto.ClienteDTO;
import com.esl.cursospring.repositories.ClienteRepository;
import com.esl.cursospring.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	/*
	 * Permite obter o parametro da uri para uso na validação de email na atualização do cadastro
	 */
	@Autowired
	private HttpServletRequest request;
	
	
	@Autowired
	private ClienteRepository repo;
	
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {//
		
		
		/*
		 * Usando MAP do pacote java.util.Map; uma coleção de chaves par e valor, será usado pois uma 
		 * requisição pode ter varios atributos e esses atributos são armazenados dentro de uma MAP que por sua vez
		 * é parecido com um JSON tendo uma chave e u valor, sendo nesse caso nscessario o Id(chave) e o valor(?)
		 * 
		 * request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) ->
		 * Pega um map de variaveis de URI que estão na requisição, sendo nescessario fazer o cast para o tipo usado
		 */
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));//Jogando o Id para uma variavel int
		
		
		List<FieldMessage> list = new ArrayList<>();
         
         /*
          * Logica para testar se o email do cliente já existe
          */
         Cliente aux = repo.findByEmail(objDto.getEmail());
         if(aux != null && !aux.getId().equals(uriId)) {//se for diferente de nulo, quer dizer que encontrou um registro com esse email e
        	 // e o id chamado é diferente do que vai ser atualizado, verificando se email ja existe em outro cadastro
        	 list.add(new FieldMessage("email", "Email já existe"));//...nesse caso passa a mensgaem customizada
         }
		
		/*
		 * Percorre a lista de FieldMessage e para cad objeto na lista será adicionado
		 * um erro correspondente na lista de erros do framework
		 */
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
	/*
	 * Logica de validação, metodo da interface ConstraintValidator que verifica se tipo ClienteNewDTO que
	 * vier de argumento vai ser valido ou não,retornando true se o objeto for valido ou falso se o objeto não for valido
	 * 
	 */
	
}
