package br.com.ufcg.ccc.psoft.dto.requests;

import br.com.ufcg.ccc.psoft.model.Veiculo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EntregadorRequestDTO {

	private Long id;

	private String nomeCompleto;

	private Veiculo veiculo;

	private String statusEstabelecimento;

	private String codigoAcesso;

}
