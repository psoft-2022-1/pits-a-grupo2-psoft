package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.exception.QuantidadeSaboresInvalidosException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;
import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import br.com.ufcg.ccc.psoft.model.Sabor;

public interface ItemDePedidoService {

    Double checkItem(ItemDePedido itemDePedido) throws QuantidadeSaboresInvalidosException, SaborNotFoundException;
}
