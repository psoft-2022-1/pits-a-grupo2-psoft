package br.com.ufcg.ccc.psoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Entregador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nomeCompleto;

	@OneToOne(targetEntity = Veiculo.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Veiculo veiculo;

	private String statusEstabelecimento;

	private String codigoAcesso;

	public Entregador() {
	}

	public Entregador(String nomeCompleto, Veiculo veiculo, String statusEstabelecimento, String codigoAcesso) {
		this.nomeCompleto = nomeCompleto;
		this.veiculo = veiculo;
		this.statusEstabelecimento = statusEstabelecimento;
		this.codigoAcesso = codigoAcesso;
	}
}
