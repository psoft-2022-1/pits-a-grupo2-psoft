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

	public Estabelecimento(String codigoAcesso, List<Funcionario> funcionarios) {
		this.codigoAcesso = codigoAcesso;
		this.cardapio = new Cardapio();
		this.funcionarios = new ArrayList<>();
	}
	
	public Cardapio getCardapio() {
		return this.cardapio;
	}
	
	public void setCardapio(Cardapio cardapio) {
		this.cardapio = cardapio;
	}
	
}
