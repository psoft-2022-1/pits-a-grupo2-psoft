package br.com.ufcg.ccc.psoft.dto.requests;

import br.com.ufcg.ccc.psoft.model.Sabor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaborRequestDTO {

	private Long id;

	private String nomeSabor;

	private String tipo; 
	
	private Double valorMedio;

	private Double valorGrande;

	public SaborRequestDTO(Sabor sabor) {
		this.id = sabor.getId();
		this.nomeSabor = sabor.getNomeSabor();
		this.tipo = sabor.getTipo();
		this.valorMedio = sabor.getValorMedio();
		this.valorGrande = sabor.getValorGrande();
	}
}
