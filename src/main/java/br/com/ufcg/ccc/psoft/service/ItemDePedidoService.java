package br.com.ufcg.ccc.psoft.service;

import java.util.List;

import br.com.ufcg.ccc.psoft.dto.requests.ItemDePedidoRequestDTO;
import br.com.ufcg.ccc.psoft.exception.QuantidadeSaboresInvalidosException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;
import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import br.com.ufcg.ccc.psoft.model.Sabor;

public interface ItemDePedidoService {

	public ItemDePedido criarItemDePedido(List<Sabor> saboresDoPedido, String tamanho, double valor);
	public Double checkItem(ItemDePedidoRequestDTO itemDePedido, List<Sabor> saboresDoPedido) throws QuantidadeSaboresInvalidosException, SaborNotFoundException;
}
