package br.com.ufcg.ccc.psoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardapioDTO {

	private Long id;

    private List <SaborDTO> sabores;

	public CardapioDTO(Long id, List<SaborDTO> sabores) {
		this.id = id;
		this.sabores = sabores;
	}

}
