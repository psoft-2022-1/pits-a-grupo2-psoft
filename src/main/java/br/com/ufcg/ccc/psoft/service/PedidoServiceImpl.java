package br.com.ufcg.ccc.psoft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.ItemDePedidoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.PedidoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.SaborCreateRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.ItemDePedidoResponseDTO;
import br.com.ufcg.ccc.psoft.dto.responses.PedidoResponseDTO;
import br.com.ufcg.ccc.psoft.exception.ClienteNotFoundException;
import br.com.ufcg.ccc.psoft.exception.EstabelecimentoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.IncorretCodigoAcessoException;
import br.com.ufcg.ccc.psoft.exception.NaoHaEntregadoresDisponiveisException;
import br.com.ufcg.ccc.psoft.exception.NomeDoSaborEIdNaoCorrespondemException;
import br.com.ufcg.ccc.psoft.exception.PagamentoInvalidException;
import br.com.ufcg.ccc.psoft.exception.PedidoComStatusIncorretoParaMudancaException;
import br.com.ufcg.ccc.psoft.exception.PedidoJaEstaProntoException;
import br.com.ufcg.ccc.psoft.exception.PedidoNaoPertenceAEsseClienteException;
import br.com.ufcg.ccc.psoft.exception.PedidoNotFoundException;
import br.com.ufcg.ccc.psoft.exception.QuantidadeSaboresInvalidosException;
import br.com.ufcg.ccc.psoft.exception.SaborNaoEstaDisponivelException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;
import br.com.ufcg.ccc.psoft.model.CartaoCredito;
import br.com.ufcg.ccc.psoft.model.CartaoDebito;
import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.model.Entregador;
import br.com.ufcg.ccc.psoft.model.Estabelecimento;
import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import br.com.ufcg.ccc.psoft.model.Pagamento;
import br.com.ufcg.ccc.psoft.model.Pedido;
import br.com.ufcg.ccc.psoft.model.Pix;
import br.com.ufcg.ccc.psoft.model.Sabor;
import br.com.ufcg.ccc.psoft.repository.EntregadorRepository;
import br.com.ufcg.ccc.psoft.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EntregadorRepository entregadorRepository;

	@Autowired
	private ItemDePedidoService itemDePedidoService;

	@Autowired
	private SaborService saborService;

	@Autowired
	private EstabelecimentoService estabelecimentoService;

	@Autowired
	public ModelMapper modelMapper;

	public PedidoResponseDTO criarPedido(Long idCliente, PedidoRequestDTO pedidoDTO)
			throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException,
			IncorretCodigoAcessoException, PagamentoInvalidException, EstabelecimentoNotFoundException,
			NomeDoSaborEIdNaoCorrespondemException, SaborNaoEstaDisponivelException {

		// verificar se ha cliente com esse codAcesso
		Cliente cliente = clienteService.checkCodAcesso(idCliente, pedidoDTO.getCodCliente());

		// recuperar o estabelecimento
		Estabelecimento estabelecimento = estabelecimentoService
				.getEstabelecimentoById(pedidoDTO.getIdEstabelecimento());

		// verificar endereco
		if (pedidoDTO.getEnderecoEntrega() == null || pedidoDTO.getEnderecoEntrega().isBlank()) {
			pedidoDTO.setEnderecoEntrega(cliente.getEnderecoPrincipal());
		}

		List<ItemDePedido> itensDePedidos = extrairItensDoPedido(pedidoDTO.getItensEscolhidos());

		Pagamento pagamento = this.setTipoPagamento(pedidoDTO.getTipoPagamento());

		Pedido pedido = new Pedido(cliente, estabelecimento, itensDePedidos, pagamento, pedidoDTO.getEnderecoEntrega(),
				pagamento.calculaDesconto(calculaTotalPedido(itensDePedidos)));
		salvarPedidoCadastrado(pedido);

		return extrairInfosPedidoParaSaida(pedido);

	}

	private List<ItemDePedido> extrairItensDoPedido(List<ItemDePedidoRequestDTO> itensDoPedidoRequest)
			throws QuantidadeSaboresInvalidosException, SaborNotFoundException, NomeDoSaborEIdNaoCorrespondemException,
			SaborNaoEstaDisponivelException {

		List<ItemDePedido> itensDePedidos = new ArrayList<>();

		for (ItemDePedidoRequestDTO itemDePedido : itensDoPedidoRequest) {

			List<Sabor> saboresDoPedido = buscarSaboresDoPedido(itemDePedido.getSabores());

			Double value = itemDePedidoService.checkItem(itemDePedido, saboresDoPedido);

			ItemDePedido itemEscolhido = itemDePedidoService.criarItemDePedido(saboresDoPedido,
					itemDePedido.getTamanho(), value);

			itensDePedidos.add(itemEscolhido);
		}

		return itensDePedidos;

	}

	private List<Sabor> buscarSaboresDoPedido(List<SaborCreateRequestDTO> idsSabores)
			throws SaborNotFoundException, NomeDoSaborEIdNaoCorrespondemException, SaborNaoEstaDisponivelException {

		List<Sabor> saboresDoPedido = new ArrayList<>();

		for (SaborCreateRequestDTO s : idsSabores) {

			Sabor sabor = saborService.getSaborId(s.getIdSabor());
			if (sabor.getNomeSabor().toUpperCase().equals(s.getNomeSabor().toUpperCase())) {

				if (!sabor.isEstaDisponivel()) {
					throw new SaborNaoEstaDisponivelException();
				}
				saboresDoPedido.add(sabor);
			} else {
				throw new NomeDoSaborEIdNaoCorrespondemException();
			}

		}

		return saboresDoPedido;
	}

	private PedidoResponseDTO extrairInfosPedidoParaSaida(Pedido pedido) {

		List<ItemDePedidoResponseDTO> itensDosPedidosSaida = new ArrayList<>();
		for (ItemDePedido item : pedido.getItensEscolhidos()) {

			List<String> nomesSabores = new ArrayList<>();
			for (Sabor sabor : item.getSabores()) {
				nomesSabores.add(sabor.getNomeSabor());
			}

			ItemDePedidoResponseDTO itSaida = new ItemDePedidoResponseDTO(nomesSabores, item.getTamanho(),
					item.getValor());
			itensDosPedidosSaida.add(itSaida);
		}

		return new PedidoResponseDTO(pedido.getId(), itensDosPedidosSaida, pedido.getPagamento().getTipoPagamento(),
				pedido.getEnderecoEntrega(), pedido.getValorTotal(), pedido.getStatusPedido());
	}

	private Pagamento setTipoPagamento(String tipoPagamento) throws PagamentoInvalidException {
		if (tipoPagamento.toUpperCase().equals("PIX")) {
			return new Pix();
		} else if (tipoPagamento.toUpperCase().equals("CARTÃO DE CRÉDITO")) {
			return new CartaoCredito();
		} else if (tipoPagamento.toUpperCase().equals("CARTÃO DE DÉBITO")) {
			return new CartaoDebito();
		} else {
			throw new PagamentoInvalidException();
		}
	}

	private Double calculaTotalPedido(List<ItemDePedido> itensDePedidos) {
		double total = 0;
		for (ItemDePedido item : itensDePedidos) {
			total += item.getValor();
		}
		return total;
	}

	private void salvarPedidoCadastrado(Pedido pedido) {
		pedidoRepository.save(pedido);
	}

	public void removerPedidoCadastrado(Long id) throws PedidoNotFoundException {
		Pedido pedido = getPedidoId(id);
		pedidoRepository.delete(pedido);
	}

	private Pedido getPedidoId(Long id) throws PedidoNotFoundException {
		return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException());
	}

	/*
	 * public PedidoResponseDTO atualizarPedido(Long id, PedidoRequestDTO pedidoDTO)
	 * throws PedidoNotFoundException { Pedido pedido = getPedidoId(id);
	 * 
	 * pedido.setItensEscolhidos(pedidoDTO.getItensEscolhidos());
	 * pedido.setEnderecoEntrega(pedidoDTO.getEnderecoEntrega());
	 * pedido.getPagamento().setTipoPagamento(pedidoDTO.getTipoPagamento());
	 * pedido.getPagamento().setDesconto(calculaTotalPedido(pedidoDTO.
	 * getItensEscolhidos())); this.pedidoRepository.save(pedido);
	 * 
	 * return modelMapper.map(pedido, PedidoResponseDTO.class); }
	 */

	public PedidoResponseDTO getPedidoById(Long id) throws PedidoNotFoundException {
		Pedido pedido = getPedidoId(id);
		return  extrairInfosPedidoParaSaida(pedido);
	}

	@Override
	public PedidoResponseDTO getPedidoByClienteById(ClienteRequestDTO clienteDTO, Long idPedido) {
		Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
		return pedidoRepository.findPedidoByClienteAndId(cliente, idPedido);
	}

	@Override
	public List<PedidoResponseDTO> getPedidosByCliente(ClienteRequestDTO clienteDTO) {
		Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
		return pedidoRepository.findPedidosByClienteOrderByIdDesc(cliente);
	}

	@Override
	public List<PedidoResponseDTO> getPedidosByClienteByStatus(ClienteRequestDTO clienteDTO, String status) {
		Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
		return pedidoRepository.findPedidosByClienteAndStatusPedidoOrderByIdDesc(cliente, status);
	}

	@Override
	public PedidoResponseDTO confirmarPedido(Long idPedido) throws PedidoNotFoundException, PedidoComStatusIncorretoParaMudancaException {
		Pedido pedido = getPedidoId(idPedido);
		
		if(!pedido.getStatusPedido().equals("Pedido recebido")) {
			throw new PedidoComStatusIncorretoParaMudancaException();
		}
		pedido.setStatusPedido("Pedido em preparo");
		pedidoRepository.save(pedido);

		return extrairInfosPedidoParaSaida(pedido);
	}

	@Override
	public PedidoResponseDTO finalizarPedido(Long idPedido) throws PedidoNotFoundException, PedidoComStatusIncorretoParaMudancaException {
		Pedido pedido = getPedidoId(idPedido);
		if(!pedido.getStatusPedido().equals("Pedido em preparo")) {
			throw new PedidoComStatusIncorretoParaMudancaException();
		}
			
		pedido.setStatusPedido("Pedido pronto");

		return extrairInfosPedidoParaSaida(pedido);
	}
	
	public PedidoResponseDTO atribuirPedidoAEntregador(Long idPedido) throws PedidoNotFoundException, NaoHaEntregadoresDisponiveisException {
		
		Pedido pedido = getPedidoId(idPedido);
		
		Optional<Entregador> entregador = entregadorRepository.findByStatusEstabelecimento("APROVADO");

		if (!entregador.isPresent()) {
			throw new NaoHaEntregadoresDisponiveisException();
		}
		
		pedido.setEntregador(entregador.get());
		pedido.setStatusPedido("Pedido em rota");
		pedido.notifyCliente();
		pedidoRepository.save(pedido);
		
		return extrairInfosPedidoParaSaida(pedido);
	}

	@Override
	public PedidoResponseDTO confirmarEntregaPedido(Long idPedido, Long idCliente)
			throws PedidoNotFoundException, PedidoNaoPertenceAEsseClienteException, PedidoComStatusIncorretoParaMudancaException {
		Pedido pedido = getPedidoId(idPedido);
		if (!pedido.getCliente().getId().equals(idCliente)) {
			throw new PedidoNaoPertenceAEsseClienteException();
		}
		if(!pedido.getStatusPedido().equals("Pedido em rota")) {
			throw new PedidoComStatusIncorretoParaMudancaException();
		}
		pedido.setStatusPedido("Pedido entregue");
		pedido.notifyEstabelecimento();
		this.pedidoRepository.save(pedido);
		return extrairInfosPedidoParaSaida(pedido);
	}

	@Override
	public void cancelaPedido(Long idPedido, Long idCliente)
			throws PedidoNotFoundException, PedidoJaEstaProntoException, PedidoNaoPertenceAEsseClienteException {
		Pedido pedido = getPedidoId(idPedido);

		if (pedido.getStatusPedido().equals("Pedido pronto")) {
			throw new PedidoJaEstaProntoException();
		}

		if (!pedido.getCliente().getId().equals(idCliente)) {
			throw new PedidoNaoPertenceAEsseClienteException();
		}

		pedidoRepository.delete(pedido);

	}
}
