package br.com.ufcg.ccc.psoft.dto.responses;

import br.com.ufcg.ccc.psoft.model.Sabor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaborResponseDTO {

	private Long id;

	private String nomeSabor;

	private String tipo;

	private Double valorMedio;

	private Double valorGrande;

	public SaborResponseDTO(Sabor sabor) {
		this.id = sabor.getId();
		this.nomeSabor = sabor.getNomeSabor();
		this.tipo = sabor.getTipo();
		this.valorMedio = sabor.getValorMedio();
		this.valorGrande = sabor.getValorGrande();
	}
}
