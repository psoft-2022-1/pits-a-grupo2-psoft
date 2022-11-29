package br.com.ufcg.ccc.psoft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufcg.ccc.psoft.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	Optional<Funcionario> findBynomeCompleto(String nomeCompleto);

}