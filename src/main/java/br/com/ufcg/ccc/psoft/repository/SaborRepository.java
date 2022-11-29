package br.com.ufcg.ccc.psoft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufcg.ccc.psoft.model.Sabor;

public interface SaborRepository extends JpaRepository<Sabor, Long> {

	Optional<Sabor> findByNomeSabor(String nomeSabor);

}
