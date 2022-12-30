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
	
	private boolean estaDisponivel;

	public SaborDTO(){}
	
	public SaborDTO(Long id, String nomeSabor, String tipo, Double valorMedio, Double valorGrande,
			boolean estaDisponivel) {
		super();
		this.id = id;
		this.nomeSabor = nomeSabor;
		this.tipo = tipo;
		this.valorMedio = valorMedio;
		this.valorGrande = valorGrande;
		this.estaDisponivel = estaDisponivel;
	}
	
	public SaborDTO(Sabor sabor) {
		this.id = sabor.getId();
		this.nomeSabor = sabor.getNomeSabor();
		this.tipo = sabor.getTipo();
		this.valorMedio = sabor.getValorMedio();
		this.valorGrande = sabor.getValorGrande();
		this.estaDisponivel = sabor.getEstaDisponivel();
	}

	public boolean isEstaDisponivel() {
		return estaDisponivel;
	}

	public void setEstaDisponivel(boolean estaDisponivel) {
		this.estaDisponivel = estaDisponivel;
	}
	
	
}
