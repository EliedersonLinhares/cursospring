package com.esl.cursospring.services;



import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.esl.cursospring.domain.Cidade;
import com.esl.cursospring.domain.Cliente;
import com.esl.cursospring.domain.Endereco;
import com.esl.cursospring.domain.enums.Perfil;
import com.esl.cursospring.domain.enums.TipoCliente;
import com.esl.cursospring.dto.ClienteDTO;
import com.esl.cursospring.dto.ClienteNewDTO;
import com.esl.cursospring.repositories.ClienteRepository;
import com.esl.cursospring.repositories.EnderecoRepository;
import com.esl.cursospring.security.UserSS;
import com.esl.cursospring.services.exceptions.AuthorizationException;
import com.esl.cursospring.services.exceptions.DataIntegrityException;
import com.esl.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired //Automaticamente instaciada pelo spring, injeção de depêndencia
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3service;
	
	
	
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		/*
		 * Se for igual a nulo e não tem o perfil de administrador e seu id é 
		 * diferente do id do usuario logado, joga a excessão personalizada
		 */
		if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
			throw new AuthorizationException("Acesso Negado");
		}
		
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
		/*
		 * Retorna o objeto se exitir ou jogar uma exceção caso não exista
		 * usa-se uma função lambda para retronar a exceção
		 */
		
		
		/*
		 * Optional<Cliente> obj = repo.findById(id);
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
	@Transactional
		public Cliente insert(Cliente obj) {
			obj.setId(null);
			obj = repo.save(obj);
			enderecoRepository.saveAll(obj.getEnderecos());//Salvando os endereços junto ao insert de clientes
			
			return obj;
			
		}
	
	//metodo para atualização de dados
		public Cliente update(Cliente obj) {
			Cliente newObj = find(obj.getId());//instaciando o cliente a partir do banco de dados
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
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
		}
	}
		//metodo de buscar todas as categorias
		public List<Cliente> findAll(){
			return repo.findAll();
		}
		
		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
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
		
		public Cliente  fromDTO(ClienteDTO objDto) {
			return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null,null);
			
			//Metodo auxiliar que instancia uma categoria a partir de uma categoriaDTO
		}
		
		//Metodo sobrecarregado do FromDTO a partir do ClienteNewDTO
		public Cliente fromDTO(ClienteNewDTO objDto){
			//Instanciando os dados a partir do tipo ClienteNewDTO, Clinte,Cidade e Endereço
			Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(),objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));//usando o bcrypt para encripitar a senha
			Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
			Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
			
			//Cliente conhece seus endereços
			cli.getEnderecos().add(end);
			//Cliente conhece seus telefones
			cli.getTelefones().add(objDto.getTelefone1());//obrigatorio ter ao menos um telefone
			//Testa se a pessoa inseriu o telefone 2, se sim, adiciona ao BD 
			if(objDto.getTelefone2()!=null) {
				cli.getTelefones().add(objDto.getTelefone2());
			}
			//...
			if(objDto.getTelefone3()!=null) {
				cli.getTelefones().add(objDto.getTelefone3());
			}
			return cli;
		}
		
	
		//Metodo auxiliar exclusivo dessa classe, não havendo nescessidade expor ele publicamente
		private void updateData(Cliente newObj, Cliente obj) {//o Objeto buscado tera seus valores atualizados com o que for fornecido 
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
			
			
		}
		
		public URI uploadProfilePicture(MultipartFile multipartFile) {
			return s3service.uploadFile(multipartFile);
		}
		
		
	
}
