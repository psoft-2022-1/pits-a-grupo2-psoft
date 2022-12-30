package br.com.ufcg.ccc.psoft.dto.requests;

import br.com.ufcg.ccc.psoft.model.Veiculo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EntregadorRequestDTO {

	private String nomeCompleto;

	private VeiculoRequestDTO veiculo;

	private String codigoAcesso;

}
