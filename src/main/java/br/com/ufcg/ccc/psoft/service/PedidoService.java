package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.dto.PedidoDTO;
import br.com.ufcg.ccc.psoft.exception.*;
import br.com.ufcg.ccc.psoft.model.Cliente;
import br.com.ufcg.ccc.psoft.model.Pedido;

import java.util.List;

public interface PedidoService {

    public PedidoDTO criaPedido(Long idCliente, PedidoDTO pedidoDTO) throws SaborNotFoundException, QuantidadeSaboresInvalidosException, ClienteNotFoundException, IncorretCodigoAcessoException;

    public void removerPedidoCadastrado(Long id) throws PedidoNotFoundException;

    PedidoDTO atualizarPedido(Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException;

    public PedidoDTO getPedidoById(Long idPedido) throws PedidoNotFoundException;

    public Pedido getPedidoByClienteById(ClienteDTO cliente, Long idPedido);

    public List<Pedido> getPedidosByClienteByIdProduto(ClienteDTO cliente, String Status);

}
