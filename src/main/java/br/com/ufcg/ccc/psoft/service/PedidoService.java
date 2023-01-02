package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.PedidoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.PedidoResponseDTO;
import br.com.ufcg.ccc.psoft.dto.responses.PedidoResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.model.Pedido;

import java.util.List;

public interface PedidoService {

	public PedidoResponseDTO criarPedido(Long idCliente, PedidoRequestDTO pedidoDTO) throws SaborNotFoundException,
			QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException,
			PagamentoInvalidException, EstabelecimentoNotFoundException, NomeDoSaborEIdNaoCorrespondemException,SaborNaoEstaDisponivelException;

	public void removerPedidoCadastrado(Long id) throws PedidoNotFoundException;

	// PedidoResponseDTO atualizarPedido(Long id, PedidoRequestDTO pedidoDTO) throws
	// PedidoNotFoundException;

	public PedidoResponseDTO getPedidoById(Long idPedido) throws PedidoNotFoundException;

	public PedidoResponseDTO getPedidoByClienteById(ClienteRequestDTO clienteDTO, Long idPedido);

	public List<PedidoResponseDTO> getPedidosByCliente(ClienteRequestDTO clienteDTO);

	public List<PedidoResponseDTO> getPedidosByClienteByStatus(ClienteRequestDTO clienteDTO, String status);

	PedidoResponseDTO confirmarPedido(Long id, PedidoRequestDTO pedidoDTO) throws PedidoNotFoundException;

	PedidoResponseDTO finalizarPedido(Long id, PedidoRequestDTO pedidoRequestDTO) throws PedidoNotFoundException;

	public PedidoResponseDTO confirmaPedido(Long idPedido, Long idCliente)
			throws PedidoNotFoundException, PedidoNaoPertenceAEsseClienteException;

	public void cancelaPedido(Long idPedido, Long idCliente)
			throws PedidoNotFoundException, PedidoJaEstaProntoException, PedidoNaoPertenceAEsseClienteException;

}
