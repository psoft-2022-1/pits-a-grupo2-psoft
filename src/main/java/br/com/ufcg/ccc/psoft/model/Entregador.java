package br.com.ufcg.ccc.psoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

	private String disponibilidade;

	public Entregador(String nomeCompleto, Veiculo veiculo, String codigoAcesso) {
		this.nomeCompleto = nomeCompleto;
		this.veiculo = veiculo;
		this.statusEstabelecimento = "Sob análise";
		this.codigoAcesso = codigoAcesso;
	}
}
