package br.com.ufcg.ccc.psoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufcg.ccc.psoft.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
