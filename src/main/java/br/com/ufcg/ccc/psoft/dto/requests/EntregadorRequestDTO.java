package br.com.ufcg.ccc.psoft.dto.requests;

import br.com.ufcg.ccc.psoft.model.Veiculo;
import lombok.Data;

@Data
public class EntregadorRequestDTO {

	private String nomeCompleto;

	private Veiculo veiculo;

	private String statusEstabelecimento;

	private String codigoAcesso;

	private String status;
}
