package br.com.ufcg.ccc.psoft.repository;

import br.com.ufcg.ccc.psoft.model.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntregadorRepository extends JpaRepository<Entregador, Long> {

    Optional<Entregador> findByNomeCompleto(String nome);

}
