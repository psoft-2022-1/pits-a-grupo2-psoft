package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.PedidoDTO;
import br.com.ufcg.ccc.psoft.exception.*;

public interface PedidoService {

    public PedidoDTO criaPedido(Long idCliente, PedidoDTO pedidoDTO) throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException;

    public void removerPedidoCadastrado(Long id) throws PedidoNotFoundException;

    public PedidoDTO atualizarPedido(Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException;

    public PedidoDTO getPedidoById(Long idPedido) throws PedidoNotFoundException;

	public PedidoDTO confirmaPedido(Long idPedido, Long idCliente)throws PedidoNotFoundException, PedidoNaoPertenceAEsseClienteException;

	public void cancelaPedido(Long idPedido, Long idCliente) throws PedidoNotFoundException, PedidoJaEstaProntoException, PedidoNaoPertenceAEsseClienteException;

}
