package br.com.ufcg.ccc.psoft.dto.responses;

import lombok.Data;

@Data
public class PagamentoResponseDTO {

	private Long id;

	private String tipo;

	private double valor;

}
