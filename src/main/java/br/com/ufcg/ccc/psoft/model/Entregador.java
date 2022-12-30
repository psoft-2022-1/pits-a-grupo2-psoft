package br.com.ufcg.ccc.psoft.model;

import br.com.ufcg.ccc.psoft.dto.ClienteDTO;
import br.com.ufcg.ccc.psoft.dto.EntregadorDTO;
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

	public Entregador(EntregadorDTO entregadorDTO, Veiculo veiculo) {
		this.nomeCompleto = entregadorDTO.getNomeCompleto();
		this.veiculo = veiculo;
		this.statusEstabelecimento = "SOB ANALISE";
		this.codigoAcesso = entregadorDTO.getCodigoAcesso();
	}
}
