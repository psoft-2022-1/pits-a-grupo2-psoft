package br.com.ufcg.ccc.psoft.dto.requests;

import br.com.ufcg.ccc.psoft.model.Cardapio;
import br.com.ufcg.ccc.psoft.model.Funcionario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoDTO {

	private Long id;

	private String codigoAcesso;

	private Cardapio cardapio;

	private List<Funcionario> funcionarios;

}
