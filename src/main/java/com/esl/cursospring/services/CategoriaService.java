package com.esl.cursospring.services;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.esl.cursospring.domain.Categoria;
import com.esl.cursospring.dto.CategoriaDTO;
import com.esl.cursospring.repositories.CategoriaRepository;
import com.esl.cursospring.services.exceptions.DataIntegrityException;
import com.esl.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired //Automaticamente instaciada pelo spring, injeção de depêndencia
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		
		/*
		 * Retorna o objeto se exitir ou jogar uma exceção caso não exista
		 * usa-se uma função lambda para retornar a exceção
		 */
		
		
		/*
		 * Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
		 */
		/*
		 * O SpringBoot a partir da versão 2.x passou a usar o java 8 e com isso
		 * usa o objeto Optional(Container, encapsula se o objeto está instaciado ou não(null))
		 *  para manter o mesmo contrato,
		 * Usando o findById ele retornará um objeto Optional do tipo que voce precisar
		 * 
		 * obj.orElse(null) ->  Se o objeto for encontrado ele vai ter sido instaciado, 
		 * retorna o objeto. Se o objeto não for encontrado irá retornar o valor nulo
		 */
	}
	
	//metodo para inserção de dados
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	//metodo para atualização de dados
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());//instaciando o cliente a partir do banco de dados
		updateData(newObj, obj);//Atualiza o objto atual com base no objeto que veio como argumento
		return repo.save(newObj);
	}
	
	//metodo para deleção de dados
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
	}
	catch(DataIntegrityViolationException e) {
		throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
	}
}
	//metodo de buscar todas as categorias
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
	
		//Objeto que prepara os dados para retonar a consulta com a pagina de dados, tambem é um metodo od SpringData
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	/*
	 * A O metodo "Page" do Spring encapsula informções sobre a paginação, sendo que os dados nescessarios são
	 * 
	 * Page = Qual pagina é requerida
	 * LinesPerPage = Quantas linhas por pagina(0,1,2,...)
	 * OrderBy = Por qual atributo a lista vai ser ordenada (id,nomecategoria,...)
	 * Direction = Por qual direção será ordenada ASC(ascendente) DESC(Descendente)
	 */
	
	public Categoria  fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
		
		//Metodo auxiliar que instancia uma categoria a partir de uma categoriaDTO
	}
	//Metodo auxiliar exclusivo dessa classe, não havendo nescessidade expor ele publicamente
			private void updateData(Categoria newObj, Categoria obj) {//o Objeto buscado tera seus valores atualizados com o que for fornecido 
				newObj.setNome(obj.getNome());
				
			}
	
}
