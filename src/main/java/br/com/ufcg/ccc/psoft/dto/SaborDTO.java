package br.com.ufcg.ccc.psoft.dto;

import java.util.Map;

import br.com.ufcg.ccc.psoft.model.Sabor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaborDTO {

	private Long id;

	private String nomeSabor;

	private String tipo; 
	
	private Double valorMedio;

	private Double valorGrande;

	public SaborDTO(Sabor sabor) {
		this.id = sabor.getId();
		this.nomeSabor = sabor.getNomeSabor();
		this.tipo = sabor.getTipo();
		this.valorMedio = sabor.getValorMedio();
		this.valorGrande = sabor.getValorGrande();
	}

}
