package br.com.ufcg.ccc.psoft.dto.responses;

import br.com.ufcg.ccc.psoft.model.Veiculo;
import lombok.Data;

@Data
public class EntregadorResponseDTO {

	private Long id;

	private String nomeCompleto;

	private Veiculo veiculo;

	private String statusEstabelecimento;

}
