package br.com.ufcg.ccc.psoft.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
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

	@Override
	public String toString() {
		return "Placa do veiculo: " + placaVeiculo + "\n"
				+ "Cor do veiculo: " + corVeiculo + "\n "
				+ "Tipo de veiculo: " + tipoVeiculo;
	}
}
