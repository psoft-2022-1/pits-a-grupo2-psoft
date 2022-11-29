package br.com.ufcg.ccc.psoft.dto;

import br.com.ufcg.ccc.psoft.model.Veiculo;
import lombok.Data;

@Data
public class EntregadorDTO {

	private Long id;

	private String nomeCompleto;

	private Veiculo veiculo;

	private String statusEstabelecimento;

	private String codigoAcesso;

}
