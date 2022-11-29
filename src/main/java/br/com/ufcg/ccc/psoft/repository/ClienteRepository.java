package br.com.ufcg.ccc.psoft.repository;

import br.com.ufcg.ccc.psoft.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}