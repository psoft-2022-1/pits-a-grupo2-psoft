package br.com.ufcg.ccc.psoft.dto;

import br.com.ufcg.ccc.psoft.model.Veiculo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EntregadorDTO {

	private Long id;

	private String nomeCompleto;

	private Veiculo veiculo;

	private String statusEstabelecimento;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String codigoAcesso;

	
}
