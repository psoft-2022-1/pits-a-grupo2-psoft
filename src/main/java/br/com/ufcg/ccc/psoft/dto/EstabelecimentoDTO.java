package br.com.ufcg.ccc.psoft.dto;

import br.com.ufcg.ccc.psoft.model.Cardapio;
import br.com.ufcg.ccc.psoft.model.Funcionario;
import lombok.Data;

import java.util.List;
@Data
public class EstabelecimentoDTO {

	private Long id;
	
	private String codigoAcesso;

	private Cardapio cardapio;

	private List<Funcionario> funcionarios;

}
