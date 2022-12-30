package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.exception.PagamentoNotFoundException;
import br.com.ufcg.ccc.psoft.model.Pagamento;

public interface PagamentoService {
    public Pagamento getPagamentoById(Long id) throws PagamentoNotFoundException;

}
