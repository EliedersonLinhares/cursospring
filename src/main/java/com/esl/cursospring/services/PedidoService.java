package com.esl.cursospring.services;



import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esl.cursospring.domain.Cliente;
import com.esl.cursospring.domain.ItemPedido;
import com.esl.cursospring.domain.PagamentoComBoleto;
import com.esl.cursospring.domain.Pedido;
import com.esl.cursospring.domain.enums.EstadoPagamento;
import com.esl.cursospring.repositories.ItemPedidoRepository;
import com.esl.cursospring.repositories.PagamentoRepository;
import com.esl.cursospring.repositories.PedidoRepository;
import com.esl.cursospring.security.UserSS;
import com.esl.cursospring.services.exceptions.AuthorizationException;
import com.esl.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired //Automaticamente instaciada pelo spring, injeção de depêndencia
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		
		/*
		 * Retorna o objeto se exitir ou jogar uma exceção caso não exista
		 * usa-se uma função lambda para retronar a exceção
		 */
		
		
		/*
		 * Optional<Pedido> obj = repo.findById(id);
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
	
	@Transactional
	public Pedido insert(Pedido obj) {
		
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));//incluindo produto pelo id para buscar o nome para o mockemail
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {// Se o tipo de pagamento for do tipo pagamento com boleto
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();//defini pagto com tipo PagamentoComBoleto
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj); // salva o pedido
		pagamentoRepository.save(obj.getPagamento()); //salva o pagamento
	    
		for(ItemPedido ip : obj.getItens()) {//percorre todos os ItensPedidos associados ao obj
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));//incluindo produto pelo id para buscar o nome para o mockemail
			ip.setPreco(ip.getProduto().getPreco());//preço vem do produto pelo id
			ip.setPedido(obj);
		}
	    itemPedidoRepository.saveAll(obj.getItens()); //salva os itens
	    
	    emailService.sendOrderConfirmationHtmlEmail(obj);//precisa ser instanciada no TestConfig
	    
	    return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//Verificando o cliente logado
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		//Objeto que prepara os dados para retonar a consulta com a pagina de dados, tambem é um metodo od SpringData
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());//Pegando o id do usario logado
		return repo.findByCliente(cliente, pageRequest);//usando o cliente logado 
		
	}
	
}
