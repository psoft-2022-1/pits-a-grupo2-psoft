package br.com.ufcg.ccc.psoft.dto.requests;

import br.com.ufcg.ccc.psoft.model.Veiculo;
import lombok.Data;

@Data
public class VeiculoRequestDTO {

	private String placaVeiculo;

	private String corVeiculo;

	private String tipoVeiculo;

}
