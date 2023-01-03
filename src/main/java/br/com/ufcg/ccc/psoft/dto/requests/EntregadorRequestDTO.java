package br.com.ufcg.ccc.psoft.dto.requests;

import lombok.Data;

@Data
public class EntregadorRequestDTO {

	private String nomeCompleto;

	private VeiculoRequestDTO veiculo;

	private String codigoAcesso;

}
