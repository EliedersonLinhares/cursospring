package com.esl.cursospring.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.esl.cursospring.domain.Cliente;
import com.esl.cursospring.domain.enums.TipoCliente;
import com.esl.cursospring.dto.ClienteNewDTO;
import com.esl.cursospring.repositories.ClienteRepository;
import com.esl.cursospring.resources.exception.FieldMessage;
import com.esl.cursospring.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {//
		
		List<FieldMessage> list = new ArrayList<>();
		
		/*
		 * Se for igual ao tipo CPF ou CNPJ e não for valido acresenta um erro
		 */
         if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
        	 list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
         }
         if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
        	 list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
         }
         
         /*
          * Logica para testar se o email do cliente já existe
          */
         Cliente aux = repo.findByEmail(objDto.getEmail());
         if(aux != null) {//se for diferente de nulo, quer dizer que encontrou um registro com esse email...
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
