package br.com.ufcg.ccc.psoft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String placaVeiculo;

	private String corVeiculo;

	private String tipoVeiculo;

	public Veiculo() {
	}

	public Veiculo(String placaVeiculo, String corVeiculo, String tipoVeiculo) {
		this.placaVeiculo = placaVeiculo;
		this.corVeiculo = corVeiculo;
		this.tipoVeiculo = tipoVeiculo;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public String getCorVeiculo() {
		return corVeiculo;
	}

	public String getTipoVeiculo() {
		return tipoVeiculo;
	}
}
