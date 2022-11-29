package br.com.ufcg.ccc.psoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Estabelecimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigoAcesso;

	@OneToOne(targetEntity = Cardapio.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Cardapio cardapio;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Funcionario> funcionarios;

	public Estabelecimento() {
	}

	public Estabelecimento(String codigoAcesso, Cardapio cardapio, List<Funcionario> funcionarios) {
		this.codigoAcesso = codigoAcesso;
		this.cardapio = cardapio;
		this.funcionarios = new ArrayList<>();
	}
}
