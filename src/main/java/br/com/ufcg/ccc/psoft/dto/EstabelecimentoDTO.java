package br.com.ufcg.ccc.psoft.dto;

import br.com.ufcg.ccc.psoft.model.Cardapio;
import br.com.ufcg.ccc.psoft.model.Funcionario;

import java.util.List;

public class EstabelecimentoDTO {

	private Long id;
	
	private String codigoAcesso;

	private Cardapio cardapio;

	private List<Funcionario> funcionarios;

	public String getCodigoAcesso() {
		return codigoAcesso;
	}

	public Cardapio getCardapio() {
		return cardapio;
	}

	public Long getId() {
		return id;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
}
