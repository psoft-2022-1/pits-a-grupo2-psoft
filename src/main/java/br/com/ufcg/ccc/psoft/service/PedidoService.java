package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.dto.PedidoDTO;
import br.com.ufcg.ccc.psoft.exception.PedidoNotFoundException;

public interface PedidoService {

    public PedidoDTO criaPedido(PedidoDTO pedidoDTO);

    public void removerPedidoCadastrado(Long id) throws PedidoNotFoundException;

    PedidoDTO atualizarPedido(Long id, PedidoDTO pedidoDTO) throws PedidoNotFoundException;

}
