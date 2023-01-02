package br.com.ufcg.ccc.psoft.service;

import java.util.List;

import br.com.ufcg.ccc.psoft.dto.requests.ClienteRequestDTO;
import br.com.ufcg.ccc.psoft.dto.requests.PedidoRequestDTO;
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

	public PedidoResponseDTO confirmarPedido(Long idPedido) throws PedidoNotFoundException, PedidoComStatusIncorretoParaMudancaException;

	public PedidoResponseDTO finalizarPedido(Long idPedido) throws PedidoNotFoundException,PedidoComStatusIncorretoParaMudancaException;

	public PedidoResponseDTO atribuirPedidoAEntregador(Long idPedido) throws PedidoNotFoundException, NaoHaEntregadoresDisponiveisException ;
	
	public PedidoResponseDTO confirmarEntregaPedido(Long idPedido, Long idCliente)
			throws PedidoNotFoundException, PedidoNaoPertenceAEsseClienteException,PedidoComStatusIncorretoParaMudancaException;

	public void cancelaPedido(Long idPedido, Long idCliente)
			throws PedidoNotFoundException, PedidoJaEstaProntoException, PedidoNaoPertenceAEsseClienteException;

}
