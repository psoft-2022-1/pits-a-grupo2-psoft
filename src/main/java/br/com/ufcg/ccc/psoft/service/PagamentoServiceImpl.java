package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.exception.PagamentoNotFoundException;
import br.com.ufcg.ccc.psoft.model.Pagamento;
import br.com.ufcg.ccc.psoft.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Override
    public Pagamento getPagamentoById(Long id) throws PagamentoNotFoundException {
        return null;
    }
}
