package br.com.ufcg.ccc.psoft.dto.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaborRequestDTO {

	private String nomeSabor;

	private String tipo; 
	
	private Double valorMedio;

	private Double valorGrande;

}