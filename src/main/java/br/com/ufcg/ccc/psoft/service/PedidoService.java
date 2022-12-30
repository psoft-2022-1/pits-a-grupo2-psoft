package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.requests.PedidoRequestDTO;
import br.com.ufcg.ccc.psoft.dto.responses.PedidoResponseDTO;
import br.com.ufcg.ccc.psoft.exception.*;

public interface PedidoService {

    public PedidoResponseDTO criaPedido(Long idCliente, PedidoRequestDTO pedidoDTO) throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException, PagamentoInvalidException;

    public void removerPedidoCadastrado(Long id) throws PedidoNotFoundException;

    PedidoResponseDTO atualizarPedido(Long id, PedidoRequestDTO pedidoDTO) throws PedidoNotFoundException;

    public PedidoResponseDTO getPedidoById(Long idPedido) throws PedidoNotFoundException;

    PedidoResponseDTO confirmarPedido(Long id, PedidoRequestDTO pedidoRequestDTO) throws PedidoNotFoundException;

    PedidoResponseDTO finalizarPedido(Long id, PedidoRequestDTO pedidoRequestDTO) throws PedidoNotFoundException;

}
