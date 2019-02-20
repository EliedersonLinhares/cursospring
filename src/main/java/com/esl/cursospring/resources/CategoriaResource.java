package com.esl.cursospring.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController //Anotação que determina que a classe vai ser um controlador rest
@RequestMapping(value="/categorias") // respondendo pelo endpoint "/categorias
public class CategoriaResource {

	@RequestMapping(method=RequestMethod.GET) // http para obter dados
	public String listar() {
		return "REST está funcionando";
	}
}
